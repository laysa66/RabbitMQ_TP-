import sys
from common import ouvrir, traiter
prefetch = int(sys.argv[1]) if len(sys.argv) > 1 else 1
conn, ch = ouvrir()
ch.queue_declare(queue='tasks.email', durable=True)
ch.basic_qos(prefetch_count=prefetch)
def recevoir(ch, method, props, body):
    print('Reçu :', body.decode(), 'redélivré =', method.redelivered, flush=True)
    traiter(body)
    ch.basic_ack(delivery_tag=method.delivery_tag)
ch.basic_consume(queue='tasks.email', on_message_callback=recevoir, auto_ack=False)
print(f'En attente dans tasks.email ; prefetch={prefetch}', flush=True)
try:
    ch.start_consuming()
except KeyboardInterrupt:
    conn.close()
