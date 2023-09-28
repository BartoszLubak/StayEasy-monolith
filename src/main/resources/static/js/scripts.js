/*!
* Start Bootstrap - Landing Page v6.0.6 (https://startbootstrap.com/theme/landing-page)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-landing-page/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project


$(document).ready(function () {
    $('#checkInDatepicker101').datepicker({
        format: 'yyyy-mm-dd',
        todayHighlight: true
    });

    $('.btn-primary').click(function () {
        var roomId = [[${room.id}]];
        var checkInDate = $('#checkInDate101').val();
        var checkOutDate = $('#checkOutDate101').val();

        $.ajax({
            url: '/rooms/checkAvailability',
            type: 'GET',
            data: {
                room: {id: roomId},
                guestCheckIn: checkInDate,
                guestCheckOut: checkOutDate
            },
            success: function (response) {
                if (response) {
                    $('.availability-message').text('Room available between dates').show();
                } else {
                    $('.availability-message').text('Room not available between dates').show();
                }
            },
            error: function () {
                alert('Wystąpił błąd podczas sprawdzania dostępności.');
            }
        });
    });
});
