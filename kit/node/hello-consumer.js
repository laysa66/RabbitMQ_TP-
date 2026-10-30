import {ouvrir, finirSurErreur} from './common.js';
async function main() {
  const {ch} = await ouvrir();
  await ch.assertQueue('hello', {durable: true});
  await ch.consume('hello', msg => {
    if (!msg) return;
    console.log('Reçu :', msg.content.toString());
    ch.ack(msg);
  }, {noAck: false});
  console.log('En attente dans hello - Ctrl+C pour arrêter');
}
main().catch(finirSurErreur);
