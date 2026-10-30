from common import ouvrir
conn, ch = ouvrir()
ch.queue_declare(queue='hello', durable=True)
def recevoir(ch, method, props, body):
    print('Reçu :', body.decode(), flush=True)
    ch.basic_ack(delivery_tag=method.delivery_tag)
ch.basic_consume(queue='hello', on_message_callback=recevoir, auto_ack=False)
print('En attente dans hello - Ctrl+C pour arrêter', flush=True)
try:
    ch.start_consuming()
except KeyboardInterrupt:
    conn.close()
