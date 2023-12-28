var customerFormVar =document.querySelector("#customerForm");
var itemFormVar =document.querySelector("#itemForm");
var orderrFormVar =document.querySelector("#orderForm");
var homeFormVar = document.querySelector("#homeeeeee");


homeFormVar.style.display='inline'
customerFormVar.style.display='none';
itemFormVar.style.display='none';
orderrFormVar.style.display='none';


$("#homeNav").click(function (){
    homeFormVar.style.display='inline'
    customerFormVar.style.display='none';
    itemFormVar.style.display='none';
    orderrFormVar.style.display='none';
});

$("#customerNav").click(function (){
    homeFormVar.style.display='none'
    customerFormVar.style.display='inline';
    itemFormVar.style.display='none';
    orderrFormVar.style.display='none';
});


$("#itemNav").click(function (){
    homeFormVar.style.display='none'
    customerFormVar.style.display='none';
    itemFormVar.style.display='inline';
    orderrFormVar.style.display='none';
});



var $tblCustomer = $("#tblCustomer");
var $cIdTxt = $("#cIdTxt");
var $cNameTxt = $("#cNameTxt");
var $cAddressTxt = $("#cAddressTxt");
var $cSalaryText = $("#cSalaryText");


$("#cSavebtn").click(() => {
    saveCustomer();
});

$tblCustomer.on("dblclick", "tr", function () {
    const index = $(this).index();
    Customers.splice(index, 1);
    updateCustomerTable();
});

$("#cUpdateBtn").click(() => {
    updateCustomer();
});

function updateCustomerTable() {
    $tblCustomer.empty();

    customers.forEach((customer) => {
        $tblCustomer.append(`<tr><td>${customer.name}</td><td>${customer.id}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`);
    });

    $tblCustomer.find("tr").click(function () {
        const row = $(this);
        const name = row.find("td:eq(0)").text();
        const id = row.find("td:eq(1)").text();
        const address = row.find("td:eq(2)").text();
        const salary = row.find("td:eq(3)").text();

        $cNameTxt.val(name);
        $cIdTxt.val(id);
        $cAddressTxt.val(address);
        $cSalaryText.val(salary);
    });
}

$("#clearBtn").click(() => {
    $cNameTxt.val("");
    $cIdTxt.val("");
    $cAddressTxt.val("");
    $cSalaryText.val("");
});

$("#cDeleteBtn").click(() => {
    const cIdValue = $cIdTxt.val();

    for (let i = 0; i < Customers.length; i++) {
        if (Customers[i].id === cIdValue) {
            Customers.splice(i, 1);
            updateCustomerTable();
            break;
        }
    }
});

$("#cSearchBtn").click(function () {
    const searchValue = $("#cSearchTxt").val();

    if (searchValue.trim() === "") {
        alert("Please enter a valid Customer ID to search.");
        return;
    }

    const customer = Customers.find((c) => c.id === searchValue);

    if (customer) {
        $cNameTxt.val(customer.name);
        $cIdTxt.val(customer.id);
        $cAddressTxt.val(customer.address);
        $cSalaryText.val(customer.salary);
    } else {
        alert("Customer not found.");
    }
});

function saveCustomer(){
    let newCustomer = Object.assign({},customer);
    newCustomer.id = $cIdTxt.val();
    newCustomer.name = $cNameTxt.val();
    newCustomer.address = $cAddressTxt.val();
    newCustomer.salary = $cSalaryText.val();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/app/customer",
        contentType: JSON.stringify(newCustomer),
        data: JSON.stringify(newCustomer),
        success: function (resp) {
            alert("Customer Saved");
        },
        error: function (resp) {
            alert("Failed to save the customer");
        }
    })
}

function updateCustomer(){
    let newCustomer = Object.assign({},customer);
    newCustomer.id = $cIdTxt.val();
    newCustomer.name = $cNameTxt.val();
    newCustomer.address = $cAddressTxt.val();
    newCustomer.salary = $cSalaryText.val();

    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/app/customer",
        contentType: JSON.stringify(newCustomer),
        data: JSON.stringify(newCustomer),
        success: function (resp) {
            alert("Customer Updated");
        },
        error: function (resp) {
            alert("Failed to update the customer");
        }
    })
}