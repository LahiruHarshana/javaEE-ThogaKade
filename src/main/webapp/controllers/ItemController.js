var Items =[];

var customerFormVar =document.querySelector("#customerForm");
var itemFormVar =document.querySelector("#itemForm");
var orderrFormVar =document.querySelector("#orderForm");
var homeFormVAr = document.querySelector("#homeeeeee");


homeFormVAr.style.display='inline'
customerFormVar.style.display='none';
itemFormVar.style.display='none';
orderrFormVar.style.display='none';



$("#customerNav").click(function (){
    homeFormVAr.style.display='none'
    customerFormVar.style.display='inline';
    itemFormVar.style.display='none';
    orderrFormVar.style.display='none';
});


$("#itemNav").click(function (){
    homeFormVAr.style.display='none'
    customerFormVar.style.display='none';
    itemFormVar.style.display='inline';
    orderrFormVar.style.display='none';
});



var $tblItem = $("#itemTbl");
var $iIdTxt = $("#iID");
var $iNameTxt = $("#IIName");
var $iPrice = $("#i-Price");
var $iQty = $("#Iqty");


$("#iSaveBtn").click(() => {
    saveItem();

    updateItemTable();
});


$("#iUpdateBtn").click(() => {

    updateItem();

    updateItemTable();
});

function updateItemTable() {
    $tblItem.empty();

    for (let i = 0; i < Items.length; i++) {
        const id = Items[i].id;
        const name = Items[i].name;
        const price = Items[i].price;
        const Qty = Items[i].Qty;

        const row = `<tr><td>${id}</td><td>${name}</td><td>${price}</td><td>${Qty}</td></tr>`;
        $tblItem.append(row);
    }
}

$("#iclearBtn").click(() => {
    $iNameTxt.val("");
    $iIdTxt.val("");
    $iPrice.val("");
    $iQty.val("");
});

$("#iDeleteBtn").click(() => {
    deleteItem();
    updateItemTable();
});

$("#iSearchBtn").click(function () {
    const searchValue = $("#iSearchTxt").val(); // Changed to use the item ID field for searching

    if (searchValue.trim() === "") {
        alert("Please enter a valid Item ID to search.");
        return;
    }

    const item = Items.find((i) => i.id === searchValue);

    if (item) {
        $iNameTxt.val(item.name);
        $iIdTxt.val(item.id);
        $iPrice.val(item.price);
        $iQty.val(item.Qty);
    } else {
        alert("Item not found.");
    }
});

function saveItem() {
    var item = {
        code: $("#iID").val(),
        description: $("#IIName").val(),
        unitPrice: $("#i-Price").val(),
        qtyOnHand: $("#Iqty").val(),
    };

    $.ajax({
        method: "POST",
        url: "http://localhost:8080/app/item",
        contentType: "application/json",
        async: true,
        data: JSON.stringify(item),
        success: function (data) {
            alert("Item has been saved successfully");
        },
        error: function (data) {
            alert("Failed to save the item");
        },
    });
}



function updateItem() {
    var item = {
        code: $("#iID").val(),
        description: $("#IIName").val(),
        unitPrice: $("#i-Price").val(),
        qtyOnHand: $("#Iqty").val(),
    };

    $.ajax({
        method: "PUT",
        url: "http://localhost:8080/app/item",
        contentType: "application/json",
        async: true,
        data: JSON.stringify(item),
        success: function (data) {
            alert("Item has been updated successfully");
        },
        error: function (data) {
            alert("Failed to update the item");
        },
    });
}

function deleteItem() {
    var itemId = $("#iID").val();

    $.ajax({
        method: "DELETE",
        url: `http://localhost:8080/app/item/${itemId}`,
        contentType: "application/json",
        async: true,
        success: function (data) {
            alert("Item has been deleted successfully");
        },
        error: function (data) {
            alert("Failed to delete the item");
        },
    });
}

export default Items;
