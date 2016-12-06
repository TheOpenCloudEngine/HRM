#!/usr/bin/env node

/**
 * term.js
 * Copyright (c) 2012-2013, Christopher Jeffrey (MIT License)
 */

var http = require('http')
    , express = require('express')
    , io = require('socket.io')
    , pty = require('pty.js')
    , terminal = require('../');

/**
 * term.js
 */

process.title = 'term.js';

/**
 * Dump
 */

var stream;
if (process.argv[2] === '--dump') {
    stream = require('fs').createWriteStream(__dirname + '/dump.log');
}

/**
 * Open Terminal
 */

var buff = []
    , socket
    , term;

term = pty.fork(process.env.SHELL || 'sh', [], {
    name: require('fs').existsSync('/usr/share/terminfo/x/xterm-256color')
        ? 'xterm-256color'
        : 'xterm',
    cols: 120,
    rows: 40,
    cwd: process.env.HOME
});

term.on('data', function(data) {
    if (stream) stream.write('OUT: ' + data + '\n-\n');
    return !socket
        ? buff.push(data)
        : socket.emit('data', data);
});

console.log(''
    + 'Created shell with pty master/slave'
    + ' pair (master: %d, pid: %d)',
    term.fd, term.pid);

/**
 * App & Server
 */

var app = express()
    , server = http.createServer(app);

app.use(function(req, res, next) {
    var setHeader = res.setHeader;
    res.setHeader = function(name) {
        switch (name) {
            case 'Cache-Control':
            case 'Last-Modified':
            case 'ETag':
                return;
        }
        return setHeader.apply(res, arguments);
    };
    next();
});


app.use(express.static(__dirname));
app.use(terminal.middleware());

if (!~process.argv.indexOf('-n')) {
    server.on('connection', function(socket) {
    });
}

server.listen(8081);

/**
 * Sockets
 */

io = io.listen(server, {
    log: false
});

io.sockets.on('connection', function(sock) {
    socket = sock;

    socket.on('data', function(data) {
        if (stream) stream.write('IN: ' + data + '\n-\n');
        //console.log(JSON.stringify(data));
        term.write(data);
    });

    socket.on('disconnect', function() {
        socket = null;
    });

    while (buff.length) {
        socket.emit('data', buff.shift());
    }
});
