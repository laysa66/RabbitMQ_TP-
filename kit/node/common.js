import amqp from 'amqplib';
export async function ouvrir() {
  const conn = await amqp.connect(process.env.AMQP_URL ?? 'amqp://guest:guest@localhost:5672');
  const ch = await conn.createConfirmChannel();
  return {conn, ch};
}
export async function traiter(msg) {
  const text = msg.content.toString();
  console.log('Début :', text);
  const secondes = (text.match(/\./g) ?? []).length;
  await new Promise(resolve => setTimeout(resolve, secondes * 1000));
  console.log('Terminé :', text);
}
export function finirSurErreur(error) {
  console.error(error.message);
  process.exitCode = 1;
}
