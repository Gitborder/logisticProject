<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">

    <script src="${pageContext.request.contextPath}/js/modal.js?v=<%=System.currentTimeMillis()%>" defer></script>
    <script src="${pageContext.request.contextPath}/js/datepickerUse.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css">

    <style>

        #searchCustomerBox {
            display: none;
            margin-bottom: 7px;
        }

        button {
            background-color: #506FA9;
            border: none;
            color: white;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            border-radius: 3px;
            margin-bottom: 10px;
        }

        .ag-header-cell-label {
            justify-content: center;
        }
        .ag-cell-value {
            padding-left: 5px;
        }

    </style>
</head>
<body>
<article class="delivery">
<div class="delivery__Title" style="color: black">
<div>
    <h5>π νλͺ© λͺ©λ‘μ‘°ν</h5>
    <button id="itemgroupBttton">νλͺ© μ‘°ν</button>
    <button id="deliverableContractSearchButton">νλͺ© μμΈμ‘°ν</button>
    <button id="deleteitemgroupBttton">νλͺ© μ­μ </button>

</div>
    <div id="myGrid2" class="ag-theme-balham" style="height:30vh;width:auto; text-align: center;"></div>
</div>
</article>

<div>
        <h5>π νλͺ© μμΈ λͺ©λ‘μ‘°ν</h5>
        <button id="itemDetailInsertButton" >νλͺ©μμΈμΆκ°</button>
        <button id="itemDetailDeleteButton" >νλͺ©μμΈμ­μ </button>

        <button id="batchSaveButton" style="float:right; background-color:#F15F5F"  >μΌκ΄μ²λ¦¬</button>



</div>

<article class="contractGrid">
    <div align="center">
        <div id="myGrid" class="ag-theme-balham" style="height:30vh; width:auto; text-align: center;"></div>
    </div>
</article>

<script>
  const myGrid = document.querySelector("#myGrid");
  const myGrid2 = document.querySelector("#myGrid2");
  const fromToDate = document.querySelector(".fromToDate");

  const itemgroupBtn = document.querySelector("#itemgroupBttton"); //νλͺ©κ·Έλ£Ή μ‘°ν
  const deleteitemgroupBtn = document.querySelector("#deleteitemgroupBttton"); //νλͺ©κ·Έλ£Ή μ­μ 

  const deliverableContractSearchBtn = document.querySelector("#deliverableContractSearchButton"); //νλͺ©μμΈλͺ©λ‘ μ‘°ν
  const itemDetailDeleteBtn = document.querySelector("#itemDetailDeleteButton"); //νλͺ©μμΈ μ­μ 
  const itemDetailInsertBtn = document.querySelector("#itemDetailInsertButton"); //νλͺ©μμΈ μΆκ°
  const batchSaveBtn = document.querySelector("#batchSaveButton"); //μΌκ΄μ²λ¦¬

  //νλͺ©κ·Έλ£Ή κ·Έλ¦¬λ
  let clientInformationColumn = [
      {headerName: "νλͺ©κ·Έλ£Ήμ½λ", field: "itemGroupCode",
           checkboxSelection: true,
            width: 200,
            headerCheckboxSelection: true,
            headerCheckboxSelectionFilteredOnly: true,
            suppressRowClickSelection: true,
      },
        {headerName: "νλͺ©κ·Έλ£Ήλͺ", field: "itemGroupName"},
  ];
  let clientInformationRowData = [];
  let clientInformationRowNode;
  let clientInformationGridOptions = {
    columnDefs: clientInformationColumn,
    rowSelection: 'single',
    rowData: clientInformationRowData,
    /* getRowNodeId: function(data) { //RowNodeIdλ₯Ό κ°μ Έμ€λ ν¨μλ‘ κ°μ μ§μ ν΄ λλ©΄ κ·Έλ¦¬λμμ λ°μ΄ν°λ₯Ό μ¬κΈ°μ μ μν κ°μΌλ‘ μ κ·Όν  μ μλ€
      return data.contractDetailNo;                         // L> λ°μ΄ν° μ κ·Ό μμ : var rowNode = gridOptions.api.getRowNode(selectedRow.goodCd);
    }, */
    onRowClicked: function(event) {
       console.log(event);
      let selectedRows = clientInformationGridOptions.api.getSelectedRows();
      clientInformationRowNode = event;
      console.log("test selectedRows");
      console.log(selectedRows);
      console.log("test clientInformationRowNode");
      console.log(clientInformationRowNode);
    },
    defaultColDef: {editable: false, resizable : true},
    overlayNoRowsTemplate: "νλͺ© λ¦¬μ€νΈκ° μμ΅λλ€.",
    onGridReady: function(event) {////λ³΄ν΅ κ·Έλ¦¬λ μλ μ¬μ΄μ¦ λ±μ μ§μ , ready μ΄ν νμν μ΄λ²€νΈ μ½μ
      event.api.sizeColumnsToFit();
    },
    onGridSizeChanged: function(event) {
      event.api.sizeColumnsToFit();
    },
  }

  // νλͺ©κ·Έλ£Ή μ‘°ν
  const iteminquiryinformation = (itemGroupCode) => {

    ableContractInfo = {"itemGroupCode":itemGroupCode};
    ableContractInfo=encodeURI(JSON.stringify(ableContractInfo));
    console.log("itemGroupCode : "+itemGroupCode);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "${pageContext.request.contextPath}/logisticsinfo/searchitemGroupList.do"
        + "?method=searchitemGroupList"
        + "&ableContractInfo=" + ableContractInfo,
        true);
    xhr.setRequestHeader('Accept', 'application/json'); //ν΄λΌμ΄μΈνΈκ° μλ²λ‘ μ μ‘ν  λ°μ΄ν° μ§μ ()
    xhr.send();
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        let txt = xhr.responseText;
        txt = JSON.parse(txt);
        if (txt.gridRowJson == "") {
          Swal.fire("μλ¦Ό", "μ‘°ν κ°λ₯ λ¦¬μ€νΈκ° μμ΅λλ€.", "info");
          return;
        } else if (txt.errorCode < 0) {
          Swal.fire("μλ¦Ό", txt.errorMsg, "error");
          return;
        }
        console.log(txt);
        clientInformationGridOptions.api.setRowData(txt.gridRowJson);
      }
    }
  }

  //νλͺ©κ·Έλ£Ή μ‘°ν μ΄λ²€νΈ
  itemgroupBtn.addEventListener("click", () => {
     iteminquiryinformation();
  });

  //νλͺ©κ·Έλ£Ή μ­μ 
  const deleteitemgroup = (selectedRow) => {
       ableContractInfo = {"itemGroupCode":selectedRow.itemGroupCode};
       ableContractInfo=encodeURI(JSON.stringify(ableContractInfo));

       let xhr = new XMLHttpRequest(); //XMLHttpRequestμ κ°μ²΄μμ±
       xhr.open('POST', "${pageContext.request.contextPath}/logisticsinfo/deleteitemgroup.do"
           + "?method=deleteitemgroup"
           + "&ableContractInfo=" + ableContractInfo,
           true);
       xhr.setRequestHeader('Accept', 'application/json'); //ν€λ, ν€λκ°μΌλ‘ κ²μ¬
       xhr.send();
       xhr.onreadystatechange = () => { //μ΄λ²€νΈ λ°μμ΄ λ  λλ§λ€ onreadystatechange μ΄λ²€νΈ νΈλ€λ¬κ° νΈμΆ
         if (xhr.readyState == 4 && xhr.status == 200) {
           let txt = xhr.responseText;
           txt = JSON.parse(txt);
           alert("μ­μ λμμ΅λλ€.");
           if (txt.gridRowJson == "") {
             Swal.fire("μλ¦Ό", "μ‘°ν κ°λ₯ λ¦¬μ€νΈκ° μμ΅λλ€.", "info");
             return;
           } else if (txt.errorCode < 0) {
             Swal.fire("μλ¦Ό", txt.errorMsg, "error");
             return;
           }
           console.log("ttt :" + JSON.stringify(txt));
         }
       }
     }


  //νλͺ©κ·Έλ£Ή μ­μ  μ΄λ²€νΈ
  deleteitemgroupBtn.addEventListener("click", () => {
       let selectedRows = clientInformationGridOptions.api.getSelectedRows();
        console.log(selectedRows);
        if (selectedRows == "") {
          Swal.fire("μλ¦Ό", "μ νν νμ΄ μμ΅λλ€.", "info");
          return;
        }
         let selectedRow = selectedRows[0];
        if (selectedRow.clientInformationList == "") {
          Swal.fire("μλ¦Ό", "μ­μ  κ°λ₯ν λ¦¬μ€νΈκ° μμ΅λλ€.", "info");
          return;
        }

        deleteitemgroup(selectedRow);
        clientInformationGridOptions.api.updateRowData({remove:[selectedRow]});
  });


/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */




  //νλͺ© μμΈ κ·Έλ¦¬λ
  let deliverableContractColumn = [
      {headerName: "νλͺ©μ½λ", field: "itemCode",
           checkboxSelection: true,
            width: 200,
            headerCheckboxSelection: false,
            headerCheckboxSelectionFilteredOnly: true,
            suppressRowClickSelection: true,
            editable: true

      },
      {headerName: "νλͺ©λͺ", field: "itemName", editable: true},
      {headerName: "νλͺ©κ·Έλ£Ήμ½λ", field: "itemGroupCode", editable: true},
       {headerName: "νλͺ©λΆλ₯", field: "itemClassification", editable: true},
       {headerName: "λ¨μ", field: "unitOfStock", editable: true},
       {headerName: "μμ€μ¨", field: "lossRate", editable: true},
       {headerName: "μμμΌ", field: "leadTime", editable: true},
       {headerName: "λ¨κ°", field: "standardUnitPrice", editable: true},
       {headerName: "μ€λͺ", field: "description", editable: true},
       {headerName: "status", field: "status"}

  ];
  let deliverableContractRowData = [];
  let deliverableContractGridOptions = {
    columnDefs: deliverableContractColumn,
    rowSelection: 'multiple',
    rowData: deliverableContractRowData,
 /*    getRowNodeId: function(data) {
      return data.itemGroupCode;
    }, */

    defaultColDef: {editable: false, resizable : true},
    overlayNoRowsTemplate: "νλͺ© μμΈ λ¦¬μ€νΈκ° μμ΅λλ€.",
    onGridReady: function(event) { //RowNodeIdλ₯Ό κ°μ Έμ€λ ν¨μλ‘ κ°μ μ§μ ν΄ λλ©΄ κ·Έλ¦¬λμμ λ°μ΄ν°λ₯Ό μ¬κΈ°μ μ μν κ°μΌλ‘ μ κ·Όν  μ μλ€
      event.api.sizeColumnsToFit();              // L> λ°μ΄ν° μ κ·Ό μμ : var rowNode = gridOptions.api.getRowNode(selectedRow.goodCd);
    },
    onGridSizeChanged: function(event) { //λ³΄ν΅ κ·Έλ¦¬λ μλ μ¬μ΄μ¦ λ±μ μ§μ , ready μ΄ν νμν μ΄λ²€νΈ μ½μ
      event.api.sizeColumnsToFit();
    },
    onRowSelected: function (event) { // checkbox
        console.log(event);
    },
    onSelectionChanged(event) { //Rowκ° μ νμ΄ λ°λλ κ²½μ° λ°μνλ μ΄λ²€νΈ
        console.log("onSelectionChanged" + event);
    },
    onCellEditingStarted: function(event) {
		let updaterow = this.api.getSelectedRows();
		console.log(updaterow);
		if (updaterow == "") {
		   Swal.fire("μλ¦Ό", "μ νν νμ΄ μμ΅λλ€.", "info");
		     return;
		 }
		updaterow.forEach(function(updaterow,index){
			console.log(updaterow);
			if(updaterow.status == 'NORMAL'){
			   updaterow.status = 'UPDATE'
			   deliverableContractGridOptions.api.updateRowData({update: [updaterow]});
			}
		});
    },
    getSelectedRowData() {
        let selectedNodes = this.api.getSelectedNodes();
        let selectedData = selectedNodes.map(node => node.data);
        return selectedData;
       },

 }

  // νλͺ© μμΈλͺ©λ‘μ‘°ν
  const deliverableContract = (selectedRow) => {

   deliverableContractGridOptions.api.setRowData([]);
    ableContractInfo = {"itemGroupCode":selectedRow.itemGroupCode};
    ableContractInfo=encodeURI(JSON.stringify(ableContractInfo));

    let xhr = new XMLHttpRequest();
    xhr.open('POST', "${pageContext.request.contextPath}/logisticsinfo/searchitemInfoList.do"
        + "?method=searchitemInfoList"
        + "&ableContractInfo=" + ableContractInfo,
        true);
    xhr.setRequestHeader('Accept', 'application/json'); //ν΄λΌμ΄μΈνΈκ° μλ²λ‘ μ μ‘ν  λ°μ΄ν° μ§μ ()
    xhr.send();
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        let txt = xhr.responseText;
        txt = JSON.parse(txt);
        if (txt.gridRowJson == "") {
          Swal.fire("μλ¦Ό", "μ‘°ν κ°λ₯ λ¦¬μ€νΈκ° μμ΅λλ€.", "info");
          return;
        } else if (txt.errorCode < 0) {
          Swal.fire("μλ¦Ό", txt.errorMsg, "error");
          return;
        }
        console.log(txt);
        deliverableContractGridOptions.api.setRowData(txt.gridRowJson);
      }
    }
  }


  //λͺ©λ‘μμΈμ‘°ν μ΄λ²€νΈ
  deliverableContractSearchBtn.addEventListener("click", () => {  //νλͺ© λͺ©λ‘μ‘°ν
     let selectedRows = clientInformationGridOptions.api.getSelectedRows();
     console.log(selectedRows);
       if (selectedRows == "") {
         Swal.fire("μλ¦Ό", "μ νν νμ΄ μμ΅λλ€.", "info");
         return;
       }
        let selectedRow = selectedRows[0];
       if (selectedRow.clientInformationList == "") {
         Swal.fire("μλ¦Ό", "μ‘°νλλ λ¦¬μ€νΈκ° μμ΅λλ€.", "info");
         return;
       }
     deliverableContract(selectedRow);
  });



      //νλͺ©μμΈ μΆκ°
     itemDetailInsertBtn.addEventListener("click", () => {  //νλͺ© λͺ©λ‘μ‘°ν
          let row = {
        		 description: "",
                 standardUnitPrice: 0,
                 leadTime: "",
                 lossRate: "",
                 unitOfStock: "",
                 itemClassification: "",
                 itemGroupCode: "",
                 itemName: "",
                 itemCode: "",
                 status: "INSERT"
                };
                deliverableContractGridOptions.api.updateRowData({add: [row]});
       });

  //μμΈνλͺ© μ­μ 
  itemDetailDeleteBtn.addEventListener("click", () => {
   let selected = deliverableContractGridOptions.api.getSelectedRows();
     console.log(selected);
       if (selected == "") {
         Swal.fire("μλ¦Ό", "μ νν νμ΄ μμ΅λλ€.", "info");
         return;
   }
    /*  let selectedRows = deliverableContractGridOptions.api.getSelectedRows();
     console.log("μ νλ ν" + selectedRows ); */

     selected.forEach(function(selected,index){
     console.log(selected);
        if(selected.status == 'INSERT')
           deliverableContractGridOptions.api.updateRowData({remove: [selected]});
        else{
          selected.status = 'DELETE'
             deliverableContractGridOptions.api.updateRowData({update: [selected]});
       }
      });
  });

 // μΌκ΄μ²λ¦¬ <= μ νλ ν­λͺ©λ§
  batchSaveButton.addEventListener("click", () => {
    let newitemRowValue = deliverableContractGridOptions.getSelectedRowData();
    if(newitemRowValue[0].standardUnitPrice==""){
    	newitemRowValue[0].standardUnitPrice = 0;
    }else{
    	newitemRowValue[0].standardUnitPrice = parseInt(newitemRowValue[0].standardUnitPrice);
    }
    console.log(newitemRowValue);
	
    let selectedRows = deliverableContractGridOptions.api.getSelectedRows();
    if(selectedRows.length==0){
       Swal.fire({
            text: "μμΈ λͺ©λ‘μ μΆκ°νμ§ μμμ΅λλ€",
            icon: "info",
          })
       return;
    }
    for(index in selectedRows){
          selectedRow=selectedRows[index];
          console.log(selectedRow);
       if (selectedRow.itemCode == "") {
           Swal.fire({
             text: "νλͺ©μ½λλ₯Ό μλ ₯νμ§ μμμ΅λλ€",
             icon: "info",
      })
         return;
      }else if (selectedRow.itemName == "") {
          Swal.fire({
              text: "νλͺ©λͺλ₯Ό μλ ₯νμ§ μμμ΅λλ€",
              icon: "info",
          })
         return;
     }
    }

    newitemRowValue = deliverableContractGridOptions.getSelectedRowData();
    console.log('@@@@@@@@@@@@ HERE!!!@@@@@@@@@@@@@')

    Swal.fire({
      title: "νλͺ©μμΈλ₯Ό λ±λ‘νμκ² μ΅λκΉ?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'μ·¨μ',
      confirmButtonText: 'νμΈ',
    }).then( (result) => {
      if (result.isConfirmed) {

         ableContractInfo = {"newitemRowValue":newitemRowValue};
         ableContractInfo=encodeURI(JSON.stringify(newitemRowValue));
         newitemRowValue = JSON.stringify(newitemRowValue);
         let xhr = new XMLHttpRequest();
           xhr.open('POST', "${pageContext.request.contextPath}/logisticsinfo/batchSave.do"
               + "?method=batchSave"
               + "&ableContractInfo=" + ableContractInfo,
               true);
      xhr.setRequestHeader('Accept', 'application/json');
      xhr.send();
      xhr.onreadystatechange = () => {
        if (xhr.readyState == 4 && xhr.status == 200) {
          // μ΄κΈ°ν
          deliverableContractGridOptions.api.setRowData([]);
          deliverableContractGridOptions.api.setRowData([]);
          let txt = xhr.responseText;
          txt = JSON.parse(txt);
          let resultMsg =
              "<h5>< νλͺ©μμΈ λ±λ‘ λ΄μ­ ></h5>"
              + "μμ κ°μ΄ μμμ΄ μ²λ¦¬λμμ΅λλ€";
          Swal.fire({
            title: "λ±λ‘μ΄ μλ£λμμ΅λλ€.",
            html: resultMsg,
            icon: "success",
          });
        }
      };
    }})
  })
   document.addEventListener('DOMContentLoaded', () => {
   new agGrid.Grid(myGrid2, clientInformationGridOptions);
   new agGrid.Grid(myGrid, deliverableContractGridOptions);
  })
  </script>
</body>