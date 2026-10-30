import {ouvrir, finirSurErreur} from './common.js';
async function main() {
  const {conn, ch} = await ouvrir();
  try {
    await ch.assertQueue('hello', {durable: true});
    const body = Buffer.from('Bonjour RabbitMQ !');
    // TODO H : publier body dans hello.
    throw new Error('TODO H : publication à compléter');
    await ch.waitForConfirms();
  } finally { await conn.close(); }
}
main().catch(finirSurErreur);
