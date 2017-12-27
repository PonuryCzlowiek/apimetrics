$(document).ready(function() {
    var ctx = document.getElementById("memory-chart-area").getContext("2d");
    window.memoryBarChart = new Chart(ctx, config);
    getData();
});

var config = {
    type: 'line',
    data: {
        datasets: [{
            data: [
            ],
            label: 'total',
            borderColor: "#3e95cd",
            fill: false
        }, {
            data: [
            ],
            borderColor: "#cd494c",
            fill: false,
            label: 'max'
        }, {
            data: [
            ],
            borderColor: "#40cd3a",
            fill: false,
            label: 'free'
        }]
    },
    options: {
        responsive: true
    }
};

function getData() {
    $.get("/memoryUsage", function (data) {
        resetChart(data);
    })
}

function resetChart(data) {
    config.data.datasets[0].data = data.map(function(x) { return x.total / 1024 / 1024 });
    config.data.datasets[1].data = data.map(function(x) { return x.max / 1024 / 1024 });
    config.data.datasets[2].data = data.map(function(x) { return x.free / 1024 / 1024 });
    config.data.labels = data.map(function(x) { return new Date(x.time*1000).toUTCString()});
    window.memoryBarChart.update();
}