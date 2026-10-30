import {ouvrir, finirSurErreur} from './common.js';
async function main() {
  const messages = process.argv.slice(2);
  if (!messages.length) throw new Error("Usage : node task-send.js 'Email 1.....'");
  const {conn, ch} = await ouvrir();
  try {
    await ch.assertQueue('tasks.email', {durable: true});
    for (const text of messages) {
      ch.sendToQueue('tasks.email', Buffer.from(text), {persistent: true});
      console.log('Envoyé :', text);
    }
    await ch.waitForConfirms();
  } finally { await conn.close(); }
}
main().catch(finirSurErreur);
