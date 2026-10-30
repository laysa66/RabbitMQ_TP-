import {ouvrir, finirSurErreur} from './common.js';
async function main() {
  const args = process.argv.slice(2);
  const keys = args.length ? args : ['commande.creee', 'commande.annulee', 'paiement.refuse'];
  const {conn, ch} = await ouvrir();
  try {
    await ch.assertExchange('events', 'topic', {durable: true});
    for (const key of keys) {
      const body = Buffer.from('message ' + key);
      // TODO T2 : publier body dans events avec cette routing key.
      throw new Error('TODO T2 : publication à compléter');
    }
    await ch.waitForConfirms();
  } finally { await conn.close(); }
}
main().catch(finirSurErreur);
