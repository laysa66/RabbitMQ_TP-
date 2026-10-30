import {ouvrir, finirSurErreur} from './common.js';
async function main() {
  const [name, ...keys] = process.argv.slice(2);
  if (!name || !keys.length) throw new Error("Usage : node topic-consumer.js stock 'commande.*'");
  const {conn, ch} = await ouvrir();
  try {
    await ch.assertExchange('events', 'topic', {durable: true});
    const queue = `${name}.queue`;
    await ch.assertQueue(queue, {durable: true});
    for (const key of keys) {
      // TODO T1 : lier queue à events avec cette binding key.
      throw new Error('TODO T1 : binding à compléter');
    }
    await ch.consume(queue, msg => {
      if (!msg) return;
      console.log(`[${name}] ${msg.fields.routingKey} -> ${msg.content.toString()}`);
      ch.ack(msg);
    }, {noAck: false});
    console.log(`En attente : ${queue}`);
  } catch (error) { await conn.close(); throw error; }
}
main().catch(finirSurErreur);
