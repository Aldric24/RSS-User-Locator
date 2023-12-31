const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

// MySQL connection
const connection = mysql.createConnection({
    host: 'seklys.ila.lt',
    user: 'stud',
    password: 'vLXCDmSG6EpEnhXX',
    database: 'LDB'
});

connection.connect(error => {
    if (error) throw error;
    console.log("Successfully connected to the database.");
});

const port = 80;
app.listen(port, () => {
    console.log(`Server is running on port ${port}.`);
});

app.get('/', (req, res) => {
    res.send('Server is running live...');
});

app.get('/matavimai', (req, res) => {
    connection.query('SELECT * FROM matavimai', (error, results) => {
        if (error) throw error;
        res.send(results);
    });
});

app.get('/stiprumai', (req, res) => {
    connection.query('SELECT * FROM stiprumai', (error, results) => {
        if (error) throw error;
        res.send(results);
    });
});

app.get('/vartotojai', (req, res) => {
    connection.query('SELECT * FROM vartotojai', (error, results) => {
        if (error) throw error;
        res.send(results);
    });
});
