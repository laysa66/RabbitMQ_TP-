import sys
from common import ouvrir
if len(sys.argv) < 3:
    raise SystemExit("Usage : python topic-consumer.py stock 'commande.*'")
name, *keys = sys.argv[1:]
conn, ch = ouvrir()
try:
    ch.exchange_declare(exchange='events', exchange_type='topic', durable=True)
    queue = f'{name}.queue'
    ch.queue_declare(queue=queue, durable=True)
    for key in keys:
        # TODO T1 : lier queue à events avec cette binding key.
        raise NotImplementedError('TODO T1 : binding à compléter')
    def recevoir(ch, method, props, body):
        print(f'[{name}] {method.routing_key} -> {body.decode()}', flush=True)
        ch.basic_ack(delivery_tag=method.delivery_tag)
    ch.basic_consume(queue=queue, on_message_callback=recevoir, auto_ack=False)
    print(f'En attente : {queue}', flush=True)
    ch.start_consuming()
except KeyboardInterrupt:
    pass
finally:
    conn.close()
