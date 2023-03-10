<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
    <script src="${pageContext.request.contextPath}/js/modal.js?v=<%=System.currentTimeMillis()%>" defer></script>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css">
    <script>
      // O setting datapicker
      $(function() {
        // o set searchDate
        $('[data-toggle="datepicker"]').datepicker({
          autoHide: true,
          zIndex: 2048,
          startDate: new Date(),
          endDate: '0d',
          todayHiglght: true,
          autoaShow: true,
        });
      });
    </script>
    <style>
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
            padding-left: 12px;
        }

        .form-control {
            display: inline;
        !important;
        }

        #orderModal {
            position: absolute !important;
            z-index: 3000;
        }

        @media (min-width: 768px) {
            .modal-xl {
                width: 90%;
                max-width: 1700px;
            }
        }
    </style>
</head>
<body>
<article class="workOrder">
    <div class="workOrder__Title" style="color: black">
        <h5>๐ญ ์์์ง์</h5>
        <b>ํ์๋ชฉ๋ก ์กฐํ / ์์์ง์(BY MRP)</b><br>
        <button id="workOrderListButton">์กฐํ</button>
        <button id="showWorkOrderSimulationByMrpButton"  style="background-color:#F15F5F">๋ชจ์์์์ง์</button>
    </div>
</article>
<article class="myGrid">
    <div align="center">
        <div id="myGrid" class="ag-theme-balham" style="height:30vh; width:auto; text-align: center;"></div>
    </div>
</article>
<article class="workOrderInfo">
    <div class="workOrderInfo" style="color: black">
        <h5>๐ ์์์ง์ํํฉ</h5>
        <b>์์์ง์ ์กฐํ / ๋ฑ๋ก</b><br/>
        <button id="workOrderInfoListButton">์์์ง์ ์กฐํ</button>
        <button id="workOrderCompletionButton"  style="background-color:#F15F5F">์์์๋ฃ ๋ฑ๋ก</button>
    </div>
</article>
<article class="myGrid2">
    <div align="center">
        <div id="myGrid2" class="ag-theme-balham" style="height:35vh; width:auto; text-align: center;"></div>
    </div>
</article>
<%--O WORKORDER MODAL--%>
<div class="modal fade" id="workOrderModal" role="dialog">
    <div class="modal-dialog modal-xl">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5>WORKORDER</h5>
                    <input type="text" data-toggle="datepicker" id="workOderDate" placeholder="์์์ผ์ ๐" size="10"
                           autocomplete="off" style="text-align: center">&nbsp&nbsp
                    <input type="text" placeholder="์ฌ์์ฅ์ฝ๋" id="workPlaceName" value="${sessionScope.workplaceName}"
                           size="12">
                    <select type="text" placeholder="์์ฐ๊ณต์?์ฝ๋" id="productionProcess" style="width: 130px; height: 26px">
                    </select>
                    <button id="workOrderButton">๋ชจ์์?๊ฐ๋ ๊ฒฐ๊ณผ ์์์ง์</button>
                </div>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px; height: 35px">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <div id="workOrderSimulationGrid" class="ag-theme-balham" style="height: 40vh;width:auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script>

// ----------------------------------------------์์์ง์-----------------------------------------------------------------//

 //Grid ๋ฒํผ
  const myGrid = document.querySelector("#myGrid");
  const myGrid2 = document.querySelector("#myGrid2");
 //์์์ง์ ๋ฒํผ 
  const workOrderListBtn = document.querySelector("#workOrderListButton");  // ์กฐํ ๋ฒํผ
  const showWorkOrderSimulationByMrpBtn = document.querySelector("#showWorkOrderSimulationByMrpButton");  // ๋ชจ์์์์ง์ ๋ฒํผ
  //์์์ง์ ๋ชจ๋ฌ์ฐฝ
  const workOderDate = document.querySelector("#workOderDate");             //๋ชจ์์?๊ฐ ๋ฌ๋?ฅ                      
  const workOrderBtn = document.querySelector("#workOrderButton");         // ๋ชจ์์?๊ฐ๋๊ฒฐ๊ณผ ์์์ง์
  const productionProcess = document.querySelector("#productionProcess"); // ์์ฐ๊ณต์?์ฝ๋
  // ์์์ง์ ํํฉ
  const workOrderInfoListBtn = document.querySelector("#workOrderInfoListButton"); //์์์ง์ ์กฐํ๋ฒํผ
  const workOrderCompletionBtn = document.querySelector("#workOrderCompletionButton"); //์์์๋ฃ๋ฑ๋ก ๋ฒํผ
  
  
  // ์์์ง์ ์ ๊ทธ๋ฆฌ๋
  const workMrpColumn = [
    {
      headerName: "์์๋์?๊ฐ๋ฒํธ", field: "mrpNo", suppressSizeToFit: true, headerCheckboxSelection: false,
      headerCheckboxSelectionFilteredOnly: true, suppressRowClickSelection: true,
      checkboxSelection: true
    },
    /* {headerName: "์ฃผ์์ฐ๊ณํ๋ฒํธ", field: "mpsNo",}, */
    {headerName: '์์๋์ทจํฉ๋ฒํธ', field: "mrpGatheringNo"},
    {headerName: 'ํ๋ชฉ๋ถ๋ฅ', field: 'itemClassification', /* cellRenderer:(params) => {
        if (params.value. indexOf('๋ณธ์ฒด')>0) {
            return params.value = "๋ฐ์?ํ";
          }
          return '์์?ํ';
      } */},
    {headerName: 'ํ๋ชฉ์ฝ๋', field: 'itemCode',},
    {headerName: 'ํ๋ชฉ๋ช', field: 'itemName',},
    {headerName: '๋จ์', field: 'unitOfMrp',},
    {headerName: 'ํ์์๋', field: 'requiredAmount',},
    {headerName: '์์์ง์๊ธฐํ', field: 'orderDate',},
    {headerName: '์์์๋ฃ๊ธฐํ', field: 'requiredDate',}
  ];
  let workMrpRowData = [];
  let workMrpRowNode = [];
  const workMrpGridOptions = {
    defaultColDef: {
      flex: 1,
      minWidth: 100,
      resizable: true,
    },
    columnDefs: workMrpColumn,
    rowSelection: 'multiple',
    rowData: workMrpRowData,
    defaultColDef: {editable: false, resizable : true},
    overlayNoRowsTemplate: "์์์ง์ ๋ฆฌ์คํธ๊ฐ ์์ต๋๋ค.",
    onGridReady: function(event) {// onload ์ด๋ฒคํธ์ ์?์ฌ ready ์ดํ ํ์ํ ์ด๋ฒคํธ ์ฝ์ํ๋ค.
      event.api.sizeColumnsToFit();
    },
    onRowSelected: function(event) { // checkbox
    	
      workMrpRowNode.push(event);
      console.log(workMrpRowNode);
    },
    onGridSizeChanged: function(event) {
      event.api.sizeColumnsToFit();
    },
    getRowStyle: function(param) {
      return {'text-align': 'center'};
    },
  }
  
 // ----------------------------------------------์์์ง์ ์กฐํ-----------------------------------------------------------------//
 
// ์์์ง์์กฐํ
  workOrderListBtn.addEventListener('click', () => {   //--> P_WORK_ORDERABLE_MRP_LIST ํ๋ก์์? ํธ์ถ
    workOrderList();
  }); 
  //์์์ง์ ์กฐํํจ์ํธ์ถ
  const workOrderList = () => { 
    workMrpGridOptions.api.setRowData([]);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '${pageContext.request.contextPath}/operation/getWorkOrderableMrpList.do' +
        "?method=getWorkOrderableMrpList",
        true)
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.send();
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        let txt = xhr.responseText;
        txt = JSON.parse(txt);
        if (txt.errorCode < 0) {
          swal.fire("์ค๋ฅ", txt.errorMsg, "error");
          return;
        }else if (txt.gridRowJson == "") {
            swal.fire("์๋ฆผ", "์กฐํ๋ ์์์ง์ ๋ฆฌ์คํธ๊ฐ ์์ต๋๋ค.", "info");
            return;
        }
        console.log(txt);
        workMrpGridOptions.api.setRowData(txt.gridRowJson);
      }
    }
  }

  // ----------------------------------------------๋ชจ์์์์ง์ ๋ฒํผ-----------------------------------------------------------------//
  
  //๋ชจ์์์์ง์์์๋ถ๋ฅธ ๋ชจ๋ฌ์ฐฝ
  let _setWorkOrderSimulationModal = (function() {  // --> P_WORK_ORDER_SIMULATION ํ๋ก
	    let executed = false;
	    return function() {
	      if (!executed) {
	        executed = true;
	        setWorkOrderSimulationModal();
	      }
	    };
	  })();
  
  //๋ฆฌ์คํธ ๊ฐ์ด๋?๋ณ์ ํธ์ถ
  let _getDetailCodeList = (function() {
    let executed = false;
    return function() {
      if (!executed) {
        executed = true;
        getDetailCodeList();
      }
    };
  })();
  //๋ฆฌ์คํธ๊ฐ๋ค
  const getDetailCodeList= () => { 
	    let data = jsonData;
	    console.log(data);
	    let target = document.querySelector("#productionProcess");    //์์ฑ๋๋ชจ์์์์ง์ ํ๊ฒ์ ๋ฃ๊ธฐ 
	    for (let index of data.detailCodeList) {
	      let node = document.createElement("option");
	      if (index.codeUseCheck != "N") { //CodeUseCheck๊ฐ N์ธ๊ฒ๋ง ๊ฐ๋ฅํ๋ค.
	        node.value = index.detailCode;
	        let textNode = document.createTextNode(index.detailCodeName);
	        node.appendChild(textNode);
	        target.appendChild(node);
	      }
	    }
	  }

  // ๋ชจ์์์์ง์ ์ด๋ฒคํธ
  showWorkOrderSimulationByMrpBtn.addEventListener('click', () => {
    let selectedRows = workMrpGridOptions.api.getSelectedRows();
    if (selectedRows == "") {
      Swal.fire("์๋ฆผ", "์?ํํ ํ์ด ์์ต๋๋ค.", "info");
      return;
    }  
    getListData('PP');  // modal.js ์์ ๊ฐ๋ฃ์ด์ ๋ถ๋ฌ์ด
    //WorkOrderingNo=[]
    mrpGatheringNoList=[]
    mrpNoList=[]
    selectedRows.forEach(function(element, index, array){
    mrpGatheringNoList[index]=element.mrpGatheringNo;
    mrpNoList[index]=element.mrpNo;
    })
    console.log("์งํ์ฌํญ ํ์ธ์ฉ ๋ก๊ทธ (mrpGatheringNo)");
    _setWorkOrderSimulationModal();
    getWorkOrderSimulationModal(mrpGatheringNoList,mrpNoList);
    $("#workOrderModal").modal('show');
    setTimeout(() => {
      _getDetailCodeList();
    }, 100)

  });
  
  
  
  // ----------------------------------------------๋ชจ์์?๊ฐ๋ ์์์ง์ ๋ฒํผ ----------------------------------------------------------------// 
  //๋ชจ์์?๊ฐ๋ ์์์ง์ ๋ฒํผ 
  workOrderBtn.addEventListener('click', () => { //--> P_WORK_ORDER ํ๋ก
	  let selectedRows = workMrpGridOptions.api.getSelectedRows();
    // o ํ์ธ
    if (workOderDate.value == "") {
      Swal.fire("์๋ฆผ", "์์์ผ์๋ฅผ ์๋?ฅํ์ญ์์ค.", "info");
      return;
    }
    if (productionProcess.value == "") {
      Swal.fire("์๋ฆผ", "์์ฐ๊ณต์?์ฝ๋๋ฅผ ์๋?ฅํ์ญ์์ค.", "info");
      return
    }  
    let displayModel = workOrderSimulationGridOptions.api.getModel(); // --> ๋ชจ๋ฌ์์๋ ๊ทธ๋ฆฌ๋ ๊ฐ๋ฐํ
    let modalData = displayModel.gridApi.getRenderedNodes(); // -->  ๊ทธ๋ฆฌ๋๋ฅผ ๋๋๋งํด์ค
    console.log(modalData);
    let workOrderSelected = []; //์์์?ํ
    let workOrderList = []; // ์์์ง์
    let workItem=""; 
    let status=false;
    let lackTitle;   //
    let mrpGatheringNo=[]; //mrp ์ทจํฉํ๋ฒํธ
    let lackAmount;
    let mrpNo =[]; // mrp ๋ฒํธ
    for(let i=0;i<modalData.length;i++){
        if (modalData[i].data.stockAfterWork == "์ฌ๊ณ?๋ถ์กฑ") {
             Swal.fire("์๋ฆผ", "์ฌ๊ณ?๊ฐ ๋ถ์กฑํฉ๋๋ค.", "info");
             //data.inputAmount:400 else requiredAmount
             lackTitle=modalData[i].data.itemName;
             lackAmount=modalData[i].data.requiredAmount-modalData[i].data.inputAmount;
             modalData[i].data.requiredAmount=modalData[i].data.inputAmount;
             status=true;
             return;
           }
        if(modalData[i].data.itemClassification=="์์?ํ"||modalData[i].data.itemClassification=="๋ฐ์?ํ"||modalData[i].data.itemClassification=="์ฌ๊ณตํ"){
          	 workItem += modalData[i].data.itemName+",";
            mrpGatheringNo += modalData[i].data.mrpGatheringNo+","; //์์?, ๋ฐ์?, ์ฌ๊ณต ๋๋?์ ๋ณ์์ ๊ฐ์ ๋ด์
       }
        workOrderList.push(modalData[i].data); //  ๊ฐ๋ค์ ๋ฐฐ์ด๋ก ๋ฃ์ด์ค
     }
    selectedRows.forEach(function(element, index, array){ // ํ๋ผ๋ฏธํฐ ์์, ๋ฐฐ์ด, ๋ฐฐ์ด์์
        mrpNo[index]=element.mrpNo; //mrp ์์๋ค ๋ฐฐ์ด๋ก ๋ฃ์ด์ค
        })
      console.log("mrpNoList");
      console.log(mrpNoList);
	swalTitle="์์์ ์ง์ํ์๊ฒ?์ต๋๊น?";
	   selectedRows.forEach(function(element, index, array){
		    mrpNo[index]=element.mrpNo;
		    })
		    
		console.log(productionProcess.value);
      console.log('${sessionScope.workplaceCode}');
	//let mrpNo = selectedRows[0].mrpNo;
   // let mrpGatheringNo = workOrderList;
    // o ๋ฐ์ดํฐ ์?์ก
    Swal.fire({
      title: swalTitle,
      html: '์์์ง์ํ๋ชฉ</br>' + '<b>' + workItem + '</b>' + '</br>๋ฅผ ์์ํฉ๋๋ค.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: '์ทจ์',
      confirmButtonText: 'ํ์ธ',
    }).then((result) => {
      let productionProcessCode = productionProcess.value;
      console.log("mrpGatheringNo : " + mrpGatheringNo);
   
      if (result.isConfirmed) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/operation/workOrder.do' +
            "?method=workOrder"
   			+ "&mrpGatheringNo=" + encodeURI(JSON.stringify(mrpGatheringNo))
   			+ "&mrpNo=" + encodeURI(JSON.stringify(mrpNo))
            + "&workPlaceCode=" + "${sessionScope.workplaceCode}"
            + "&productionProcessCode=" + productionProcessCode,
            true)
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
          if (xhr.readyState == 4 && xhr.status == 200) {
            let txt = xhr.responseText;
            txt = JSON.parse(txt);
            console.log(txt);
            if (txt.errorCode < 0) {
              swal.fire("์ค๋ฅ", txt.errorMsg, "error");
              return;
            }
            Swal.fire(
                '์ฑ๊ณต!',
                '์์์ง์๊ฐ ์๋ฃ๋์์ต๋๋ค. ์์์ฅ์์ ๊ฒ์ฌ ๋ฐ ์?์ ์ํฉ์ ๋ณผ ์ ์์ต๋๋ค.',
                'success'
            )
            console.log(workMrpRowNode);
       		workMrpRowNode.forEach(function(element, index, array){
       		workMrpGridOptions.api.updateRowData({remove: [element.data]});
		    })
           // workMrpGridOptions.api.updateRowData({remove: [workMrpRowNode[].data]});
            workOrderSimulationGridOptions.api.setRowData([]);
            console.log(txt);
          }
        }
      }
    })
  }); 
  
  


  // ----------------------------------------------์์์ง์ ํํฉ----------------------------------------------------------------// 
 
  const workOrderInfoColumn = [
    {headerName: "์์์ง์์ผ๋?จ๋ฒํธ", field: "workOrderNo", headerCheckboxSelection: false,
      headerCheckboxSelectionFilteredOnly: true, suppressRowClickSelection: true,
      checkboxSelection: true},
   // {headerName: "์์๋์?๊ฐ๋ฒํธ", field: "mrpNo", width: 300},
   // {headerName: '์ฃผ์์ฐ๊ณํ๋ฒํธ', field: "mpsNo",width: 300},
    {headerName: '์์๋์ทจํฉ๋ฒํธ', field: 'mrpGatheringNo'},
    {headerName: 'ํ๋ชฉ๋ถ๋ฅ', field: 'itemClassification',},
    {headerName: 'ํ๋ชฉ์ฝ๋', field: 'itemCode'},
    {headerName: 'ํ๋ชฉ๋ช', field: 'itemName'},
    {headerName: '๋จ์', field: 'unitOfMrp'},
    {headerName: '์ง์์๋', field: 'requiredAmount'},
    {headerName: '์์ฐ๊ณต์?์ฝ๋', field: 'productionProcessCode'},
    {headerName: '์์ฐ๊ณต์?๋ช', field: 'productionProcessName'},
    {headerName: '์์์ฅ์ฝ๋', field: 'workSiteCode'},
    {headerName: '์์์ฅ๋ช', field: 'workSiteName'},
    {headerName: '์๋ฃ์ํ', field: 'completionStatus', cellRenderer: function (params) {
    if (params.value == "Y") {
      return params.value = "๐ข";
    }
    return 'โ';
    },},
    {headerName: '์์์๋ฃ๋์๋', field: 'requiredAmount', editable: false, cellRenderer: function (params) { //field : actualCompletionAmount
        console.log(params);
    	if (params.data.completionStatus == "Y" &&params.data.inspectionStatus== "Y" &&params.data.productionStatus== "Y"  ) {
        	return '๐ท' + params.value;
        }
        return '๐ท';
      },}
  ];
  let workOrderInfoRowData = [];
  const workOrderInfoGridOptions = {
    defaultColDef: {
      flex: 1,
      minWidth: 100,
      resizable: true,
    },
    columnDefs: workOrderInfoColumn,
    rowSelection: 'single',
    rowData: workOrderInfoRowData,
    defaultColDef: {editable: false, resizable : true},
    overlayNoRowsTemplate: "์กฐํ๋ ์์์ง์ํํฉ ๋ฆฌ์คํธ๊ฐ ์์ต๋๋ค.",
    onGridReady: function(event) {// onload ์ด๋ฒคํธ์ ์?์ฌ ready ์ดํ ํ์ํ ์ด๋ฒคํธ ์ฝ์ํ๋ค.
      event.api.sizeColumnsToFit();
    },
    onRowSelected: function(event) { // checkbox
    },
    onGridSizeChanged: function(event) {
      event.api.sizeColumnsToFit();
    },
    getRowStyle: function(param) {
      return {'text-align': 'center'};
    },
  }
  
  
  
  // ----------------------------------------------์์์ง์์กฐํ ๋ฒํผ ----------------------------------------------------------------// 

  workOrderInfoListBtn.addEventListener('click', () => { // ์์์ง์ ์กฐํ 
    WorkOrderInfoList();
  });
  
  const WorkOrderInfoList = () => {  // ์์์ง์ ์กฐํ 
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '${pageContext.request.contextPath}/operation/showWorkOrderInfoList.do' +
        "?method=showWorkOrderInfoList",
        true)
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.send();
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        let txt = xhr.responseText;
        txt = JSON.parse(txt);
        if (txt.errorCode < 0) {
          swal.fire("์ค๋ฅ", txt.errorMsg, "error");
          return;
        } else if (txt.gridRowJson == "") {
          swal.fire("์๋ฆผ", "์กฐํ๋ ์์์ฅ๋ฆฌ์คํธ๊ฐ ์์ต๋๋ค.", "info");
          return;
        }
        console.log(txt);
        workOrderInfoGridOptions.api.setRowData(txt.gridRowJson);
      }
    }
  }
 
  // ----------------------------------------------์์์๋ฃ๋ฑ๋ก ๋ฒํผ----------------------------------------------------------------// 
  
    //์์์๋ฃ๋ฑ๋ก ๋ฒํผ
  workOrderCompletionBtn.addEventListener('click', () => {   //--> P_WORK_ORDER_COMPLETION ํ๋ก
    workOrderCompletion();
  })
  
  // ํจ์ํธ์ถ
  const workOrderCompletion = () => {  
    workOrderInfoGridOptions.api.stopEditing();
    let selectedRows = workOrderInfoGridOptions.api.getSelectedRows();
    let selectedRow = selectedRows[0];
    if (selectedRows == "") {
      Swal.fire("์๋ฆผ", "์?ํํ ํ์ด ์์ต๋๋ค.", "info");
      return;
    }
    if (selectedRow.completionStatus != "Y")  {
      Swal.fire("์๋ฆผ", "์์๊ณต์?์ด ๋ค ๋๋์ง ์์์ต๋๋ค. ์์์ฅ์ ๋ฐฉ๋ฌธํ์ญ์์ค", "info");
      return;
    }
    /*if (selectedRow.actualCompletionAmount == undefined || selectedRow.actualCompletionAmount == "")  {
      Swal.fire("์๋ฆผ", "์์์๋ฃ์๋์ ์๋?ฅํ์ญ์์ค.", "info");
      return;
    }*/
    if (selectedRow.actualCompletionAmount > selectedRow.requiredAmount)  {
        Swal.fire("์๋ฆผ", "์์์๋ฃ์๋์ด ์ต๋์น๋ฅผ ๋์์ต๋๋ค. ์์์๋ฃ์๋์ ํ์ธํ์ญ์์ค.", "info");
        return;
      }
    //์์ฒญ ๊ธํ์ํฉ์์ ์์์ง์ํํฉ์์ ์ง์์๋๋ณด๋ค ์์์๋ฃ๋ ์๋์ด ์?์๊ฒฝ์ฐ ํด๋น ์ฌ๊ณ?๋งํผ ๋ฉํ๊ฐ๋ฅํ๊ฒ ํ๊ธฐ
       
    let confirmMsg = "์์์ ์๋ฃํฉ๋๋ค.</br>"
                   + "์์์ผ๋?จ๋ฒํธ : " + selectedRow.workOrderNo  + "</br>"
                   + "<b>์์์๋ฃ๋์๋ : " + selectedRow.requiredAmount + "</b></br>"; 
                 //selectedRow.actualCompletionAmount
    // o ๋ฐ์ดํฐ ์?์ก
    Swal.fire({
      title: '์์์ ์๋ฃํ์๊ฒ?์ต๋๊น?',
      html: confirmMsg,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: '์ทจ์',
      confirmButtonText: 'ํ์ธ',
    }).then((result) => {
      if (result.isConfirmed) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/operation/workOrderCompletion.do' +
            "?method=workOrderCompletion"
            + "&workOrderNo=" + selectedRow.workOrderNo  // ์์์ง์๋ฒํธ 
            + "&actualCompletionAmount=" + selectedRow.requiredAmount, // ์์์๋ฃ์๋ selectedRow.actualCompletionAmount
            true)
            console.log("22::"+selectedRow.workOrderNo);
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
          if (xhr.readyState == 4 && xhr.status == 200) {
            let txt = xhr.responseText;
            txt = JSON.parse(txt);
            console.log(txt); //object object
            if (txt.errorCode < 0) {
              swal.fire("์ค๋ฅ", txt.errorMsg, "error");
              return;
            }
            Swal.fire(
                '์ฑ๊ณต!',
                '์์๋ฑ๋ก์ด ์๋ฃ๋์์ต๋๋ค.',
                'success'
            )
            workOrderInfoGridOptions.api.updateRowData({remove: [selectedRow]});
            console.log("44::"+txt);
          }
        }
      }
    });
  }
  document.addEventListener('DOMContentLoaded', () => {
    new agGrid.Grid(myGrid, workMrpGridOptions);
    new agGrid.Grid(myGrid2, workOrderInfoGridOptions);
  });
</script>
</body>
</html>