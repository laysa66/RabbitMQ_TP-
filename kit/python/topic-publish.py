import sys
from common import ouvrir
keys = sys.argv[1:] or ['commande.creee', 'commande.annulee', 'paiement.refuse']
conn, ch = ouvrir()
try:
    ch.exchange_declare(exchange='events', exchange_type='topic', durable=True)
    for key in keys:
        body = ('message ' + key).encode()
        # TODO T2 : publier body dans events avec cette routing key.
        raise NotImplementedError('TODO T2 : publication à compléter')
finally:
    conn.close()
