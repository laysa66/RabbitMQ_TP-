package fr.tp;

import java.nio.charset.StandardCharsets;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class TpApplication implements CommandLineRunner {
    private final RabbitAdmin admin;
    private final RabbitTemplate rt;
    private final ConnectionFactory cf;
    private final Environment env;
    private final ConfigurableApplicationContext context;
    private SimpleMessageListenerContainer listener;

    public TpApplication(RabbitTemplate rt, ConnectionFactory cf, Environment env,
                         ConfigurableApplicationContext context) {
        this.rt = rt; this.cf = cf; this.env = env; this.context = context;
        this.admin = new RabbitAdmin(cf);
    }
    public static void main(String[] args) { SpringApplication.run(TpApplication.class, args); }
    private String texte(Message msg) { return new String(msg.getBody(), StandardCharsets.UTF_8); }
    private void ecouter(String queue, int prefetch, MessageListener callback) {
        listener = new SimpleMessageListenerContainer(cf);
        listener.setQueueNames(queue);
        listener.setConcurrentConsumers(1);
        listener.setPrefetchCount(prefetch);
        // Spring acquitte APRÈS le retour normal du callback.
        listener.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listener.setMessageListener(callback);
        context.addApplicationListener(event -> {
            if (event instanceof org.springframework.context.event.ContextClosedEvent) listener.stop();
        });
        listener.start();
        System.out.println("En attente : " + queue + " ; prefetch=" + prefetch);
    }
    private void traiter(Message msg) {
        String body = texte(msg);
        System.out.println("Début : " + body);
        try { Thread.sleep(body.chars().filter(c -> c == '.').count() * 1000); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new RuntimeException(e); }
        System.out.println("Terminé : " + body);
    }
    private void publierHello() {
        // TODO H : publier « Bonjour RabbitMQ ! » dans hello avec rt.
        throw new UnsupportedOperationException("TODO H : publication à compléter");
    }
    private void lierTopic(String queue, String key) {
        // TODO T1 : construire puis déclarer le binding queue -> events avec key.
        throw new UnsupportedOperationException("TODO T1 : binding à compléter");
    }
    private void publierTopic(String key, String body) {
        // TODO T2 : publier body dans events avec la routing key key.
        throw new UnsupportedOperationException("TODO T2 : publication à compléter");
    }
    @Override public void run(String... args) {
        System.out.println("PID : " + ProcessHandle.current().pid());
        String role = env.getProperty("tp.role", "aide");
        int prefetch = Integer.parseInt(env.getProperty("tp.prefetch", "1"));
        switch (role) {
            case "hello-send" -> {
                admin.declareQueue(new Queue("hello", true));
                publierHello(); context.close();
            }
            case "hello-consumer" -> {
                admin.declareQueue(new Queue("hello", true));
                ecouter("hello", 1, msg -> System.out.println("Reçu : " + texte(msg)));
            }
            case "task-send" -> {
                admin.declareQueue(new Queue("tasks.email", true));
                String[] messages = switch (env.getProperty("tp.lot", "six")) {
                    case "panne" -> new String[]{"Email 7.............................."};
                    case "durable" -> new String[]{"Email A", "Email B", "Email C"};
                    default -> new String[]{"Email 1.....", "Email 2.", "Email 3.....", "Email 4.", "Email 5.....", "Email 6."};
                };
                rt.invoke(ops -> {
                    for (String text : messages) {
                        Message msg = MessageBuilder.withBody(text.getBytes(StandardCharsets.UTF_8))
                            .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                        ops.send("", "tasks.email", msg);
                        System.out.println("Envoyé : " + text);
                    }
                    ops.waitForConfirmsOrDie(5000);
                    return null;
                });
                context.close();
            }
            case "task-worker" -> {
                admin.declareQueue(new Queue("tasks.email", true));
                ecouter("tasks.email", prefetch, msg -> {
                    System.out.println("Reçu : " + texte(msg) + " ; redélivré=" + msg.getMessageProperties().isRedelivered());
                    traiter(msg);
                });
            }
            case "topic-consumer" -> {
                String name = env.getProperty("tp.name");
                String keys = env.getProperty("tp.keys");
                if (name == null || keys == null) throw new IllegalArgumentException("Préciser --tp.name et --tp.keys");
                admin.declareExchange(new TopicExchange("events", true, false));
                String queue = name + ".queue";
                admin.declareQueue(new Queue(queue, true));
                for (String key : keys.split(",")) lierTopic(queue, key.trim());
                ecouter(queue, 1, msg -> System.out.println("[" + name + "] "
                    + msg.getMessageProperties().getReceivedRoutingKey() + " -> " + texte(msg)));
            }
            case "topic-publish" -> {
                admin.declareExchange(new TopicExchange("events", true, false));
                String keys = env.getProperty("tp.events", "commande.creee,commande.annulee,paiement.refuse");
                for (String key : keys.split(",")) publierTopic(key.trim(), "message " + key.trim());
                context.close();
            }
            default -> {
                System.out.println("Rôles : hello-send, hello-consumer, task-send, task-worker, topic-consumer, topic-publish");
                context.close();
            }
        }
    }
}
