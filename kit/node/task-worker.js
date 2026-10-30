import {ouvrir, traiter, finirSurErreur} from './common.js';
async function main() {
  const prefetch = Number(process.argv[2] ?? 1);
  if (!Number.isInteger(prefetch) || prefetch < 0) throw new Error('Prefetch entier positif ou nul requis');
  const {conn, ch} = await ouvrir();
  await ch.assertQueue('tasks.email', {durable: true});
  await ch.prefetch(prefetch);
  // Cette chaîne impose UN traitement à la fois, même avec prefetch=6.
  let suite = Promise.resolve();
  await ch.consume('tasks.email', msg => {
    if (!msg) return;
    suite = suite.then(async () => {
      console.log('Reçu :', msg.content.toString(), 'redélivré =', msg.fields.redelivered);
      await traiter(msg);
      ch.ack(msg);
    }).catch(async error => {
      finirSurErreur(error);
      await conn.close();
      process.exit(1);
    });
  }, {noAck: false});
  console.log(`En attente dans tasks.email ; prefetch=${prefetch}`);
}
main().catch(finirSurErreur);
