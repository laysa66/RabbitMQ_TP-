from common import ouvrir
conn, ch = ouvrir()
try:
    ch.queue_declare(queue='hello', durable=True)
    # TODO H : publier « Bonjour RabbitMQ ! » dans hello.
    # L'exchange par défaut a pour nom la chaîne vide.
    raise NotImplementedError('TODO H : publication à compléter')
finally:
    conn.close()
