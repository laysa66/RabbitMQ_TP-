import sys, pika
from common import ouvrir
messages = sys.argv[1:]
if not messages:
    raise SystemExit("Usage : python task-send.py 'Email 1.....' 'Email 2.'")
conn, ch = ouvrir()
ch.queue_declare(queue='tasks.email', durable=True)
ch.confirm_delivery()
for text in messages:
    ch.basic_publish(exchange='', routing_key='tasks.email', body=text.encode(),
                     properties=pika.BasicProperties(delivery_mode=2))
    print('Envoyé :', text)
conn.close()
