import os, time, pika
URL = os.environ.get('AMQP_URL', 'amqp://guest:guest@localhost:5672/')
def ouvrir():
    conn = pika.BlockingConnection(pika.URLParameters(URL))
    return conn, conn.channel()
def traiter(body):
    texte = body.decode()
    print('Début :', texte, flush=True)
    time.sleep(texte.count('.'))
    print('Terminé :', texte, flush=True)
