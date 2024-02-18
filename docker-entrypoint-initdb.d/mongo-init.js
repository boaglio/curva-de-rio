db = db.getSiblingDB('rinha2024q1');

db.createCollection('cliente');

db.cliente.insert({ _id: 1, saldo: 0 , limite: 100000, version: 0 });

db.cliente.insert({ _id: 2, saldo: 0, limite: 80000, version: 0  });

db.cliente.insert({ _id: 3, saldo: 0, limite: 1000000, version: 0  });

db.cliente.insert({ _id: 4, saldo: 0, limite: 10000000, version: 0  });

db.cliente.insert({ _id: 5, saldo: 0, limite: 500000, version: 0  });
