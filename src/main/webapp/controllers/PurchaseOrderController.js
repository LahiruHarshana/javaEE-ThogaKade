
import { validated1, validated2, validated3, validated4, validated5, validated6, validated7, validated8, validated9, validated10, validated11 } from "./validations/OrderValidation.js";


var Orders = [];

$(document).ready(function () {
    loadCustomerIds();
});


function loadCustomerIds() {
    let selectId = $("#selectCustomerId");
    selectId.empty();

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/pos/api/v1/customers",
        async: true,
        dataType: 'json',
        success: function (resp) {
            console.log(resp);
            for (let i = 0; i < resp.length; i++) {
                let option = `<option value="${resp[i].id}">${resp[i].id}</option>`;
                selectId.append(option);
            }
        }
    });
}

    $("#orderNav").click(function () {
        const customerFormVar = document.querySelector("#customerForm");
        const itemFormVar = document.querySelector("#itemForm");
        const orderrFormVar = document.querySelector("#orderForm");
        const homeFormVar = document.querySelector("#homeeeeee");


        let selectId = $("#selectCustomerId");
        selectId.children().remove();


        loadCustomerIds();

        homeFormVar.style.display='none'
        customerFormVar.style.display = "none";
        itemFormVar.style.display = "none";
        orderrFormVar.style.display = "inline";






    });

    $("#selectCustomerId").change(function () {
        validated1();
        validated2();
        validated3();
        validated4();
        validated5();
        validated6();
        validated7();
        validated8();
        validated9();
        validated10();
        validated11();
    });

    $("#oSelectItem").change(function () {

        validated1();
        validated2();
        validated3();
        validated4();
        validated5();
        validated6();
        validated7();
        validated8();
        validated9();
        validated10();
        validated11();
    });

    $("#addToItemBtn").click(function () {
        var price = parseInt($("#iOPrice").val());
        var qty = parseInt($("#oqty").val());
        var total = price * qty;

        var itemID = $("#itemID").val();

        if (!found) {
            const order = {
                orderID: $("#oId").val(),
                date: $("#date").val(),
                customerID: $("#CustomerIDORderForm").val(),
                itemID: itemID,
                itemName: $("#ItemNameOrder").val(),
                unitPrice: $("#iOPrice").val(),
                Qty: qty,
                total: total
            };

            Orders.push(order);
        }

        updateOrderTable();
        loadTotal();
    });
    function loadTotal() {
        var fullTotal=0;
        for (let i = 0; i < Orders.length; i++) {
            fullTotal+=parseInt(Orders[i].total);
        }
        $("#totalTxt").text(fullTotal);
        $("#OrderSubTotal").text(fullTotal);
    }

    $("#oSaveBtn").click(function () {
            $("#oId").val("");
           $("#date").val("");
             $("#CustomerIDORderForm").val("");
            $("#itemID").val("");
            $("#oCSalary").val("");
            $("#ItemNameOrder").val("");
            $("#iOPrice").val("");
            $("#oqty").val("");
            $("#iOQty").val("");
            $("#oCName").val("");
            $("#orderCashTxt").val("");
            $("#orderDiscountTxt").val("");
            $("#tblOrderBody").empty();
            $("#orderBalanceTxt").val("");
             $("#OrderSubTotal").text("");
             $("#totalTxt").text("");
            $("#selectCustomerId").children().remove();
            $("#oSelectItem").children().remove();


    });


    $("#orderCashTxt").keyup(function (e) {
        var total =parseInt($("#OrderSubTotal").text());
        var cash =parseInt($("#orderCashTxt").val());
        var balance =  cash-total;
        $("#orderBalanceTxt").val(balance);
    });


    $("#orderDiscountTxt").keyup(function (e) {
        var total =parseInt($("#totalTxt").text());
        var discount =parseInt($("#orderDiscountTxt").val());

        if ($("#orderDiscountTxt")=="" ){
            var discount =0;
        }
        if (discount===0){
            $("#OrderSubTotal").text(total);
        }else{
            var balance = total-(total*(discount/100));
            $("#OrderSubTotal").text(balance);
        }

    });

function updateOrderTable() {
    $("#tblOrderBody").empty();

    Orders.forEach((orders) => {
        $("#tblOrderBody").append(`<tr><td>${orders.itemID}</td><td>${orders.itemName}</td><td>${orders.unitPrice}</td><td>${orders.Qty}</td><td>${orders.total}</td></tr>`);
    });
}