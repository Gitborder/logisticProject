<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui-1.12.0/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.12.0/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.fileDownload.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/datepicker.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/datepickerUse.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/modal.js?v=<%=System.currentTimeMillis()%>" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        // O setting datapicker
        $(function () {
            // set default dates
            let start = new Date();
            start.setDate(start.getDate() - 20); 
            // set end date to max one year period:
            let end = new Date(new Date().setYear(start.getFullYear() + 1));
            // o set searchDate
            $('[data-toggle="datepicker"]').datepicker({
                autoHide: true,
                zIndex: 2048,
            });
            $('#datepicker').datepicker({
                todayHiglght: true,
                autoHide: true,
                autoaShow: true,
            });
            // o set searchRangeDate
            $('#fromDate').datepicker({
                startDate: start,
                endDate: end,
                minDate: "-10d",
                todayHiglght: true,
                autoHide: true,
                autoaShow: true,
                // update "toDate" defaults whenever "fromDate" changes
            })
            $('#toDate').datepicker({
                startDate: start,
                endDate: end,
                todayHiglght: true,
                autoHide: true,
                autoaShow: true,
            })
            $('#fromDate').on("change", function () {
                //when chosen from_date, the end date can be from that point forward
                var startVal = $('#fromDate').val();
                $('#toDate').data('datepicker').setStartDate(startVal);
            });
            $('#toDate').on("change", function () {
                //when chosen end_date, start can go just up until that point
                var endVal = $('#toDate').val();
                $('#fromDate').data('datepicker').setEndDate(endVal);
            });

        });

        
    </script>
    <style>
        .fromToDate {
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
            padding-left: 20px;
        }

        .form-control {
            display: inline;
             !important;
        }

        @media (min-width: 768px) {
            .modal-xl {
                width: 90%;
                max-width: 1200px;
            }
        }
    </style>
</head>

<body>
    <article class="contract">
        <div class="contract__Title">
            <h4>??????</h4>
            <div>
                <label for="estimateDateRadio">????????????</label>
                <input type="radio" name="searchDateCondition" value="estimateDate" id="estimateDateRadio" checked>
                &nbsp<label for="effectiveDateRadio">????????????</label>
                <input type="radio" name="searchDateCondition" value="effectiveDate" id="effectiveDateRadio">
            </div>

            <form autocomplete="off">
                <div class="fromToDate">
                    <input type="text" id="fromDate" placeholder="YYYY-MM-DD ????" size="15" style="text-align: center">
                    &nbsp ~ &nbsp<input type="text" id="toDate" placeholder="YYYY-MM-DD ????" size="15"
                        style="text-align: center">
                </div>
            </form>
            <button id="estimateSearchButton">????????????</button>
            <button id="estimateDeleteButton">????????????</button>
            <button id="pdfOpenButton">PDF ??????/??????</button>
            <button id="excelOpenButton">EXCEL ??????</button>
            <button id="pdfsend">????????? ????????? ???????????? ?????????</button>
        </div>
    </article>
    <!-- ?????? ????????? ????????? ??????????????? ???????????? ?????? ??????????????? ?????????. -->
	<div title="ExcelFile Downloading..." id="preparing-file-modal" style="display: none;">
	    <div id="progressbar" style="width: 100%; height: 22px; margin-top: 20px;"></div>
	</div>
	<!-- ??????????????? ????????? ????????? ??????????????? ?????????. -->
	<div title="Error" id="error-modal" style="display: none;">
	    <p>????????????.</p>
	</div>
    <article class="estimuloInfoGrid">
        <div align="center">
            <div id="myGrid" class="ag-theme-balham" style="height:30vh; width:auto; text-align: center;"></div>
        </div>
    </article>
    <br/>
    <button id="estimateDetailInsertButton" onclick="addRow(this)">??????????????????</button>
    <button id="estimateDetailDeleteButton" onclick="deleteRow(this)">???????????????????????????</button>
    <button id="batchSaveButton">????????????</button>
    <article class="estimateDetail">
    <div class="estimateDetail__Title">
        <h4>????????????</h4>
        <div class="menuButton">
            <div class="menuButton__selectCode">
            	<button id="estimateDetaillButton">???????????? ??????</button>
                <button class="search" id="itemList" data-toggle="modal"
                        data-target="#itemModal">????????????
                </button>
                <button class="search" id="unitList" data-toggle="modal"
                        data-target="#unitModal">????????????
                </button>
                <button class="search" id="amountList" data-toggle="modal"
                        data-target="#amountModal">??????
                </button>
            </div>
        </div>
    </div>
</article>
    
    <article class="myGrid2">
        <div align="center">
            <div id="myGrid2" class="ag-theme-balham" style="height: 30vh;width:auto; text-align: center;"></div>
        </div>
    </article>

<%--Customer Modal--%>
<div class="modal fade" id="customerModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">CUSTOMER CODE</h4>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div id="customerGrid" class="ag-theme-balham" style="height:500px;width:auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
    

<%--Item Modal--%>
<div class="modal fade" id="itemModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">ITEM CODE</h4>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div id="itemGrid" class="ag-theme-balham" style="height:500px;width:auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--Unit Modal--%>
<div class="modal fade" id="unitModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">UNIT CODE</h4>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div id="unitGrid" class="ag-theme-balham" style="height:500px;width:auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--Amount Modal--%>
<div class="modal fade" id="amountModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">AMOUNT</h4>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div style="width:auto; text-align:left">
                    <label style='font-size: 20px; margin-right: 10px'>????????????</label>
                    <input type='text' id='estimateAmountBox' autocomplete="off"/><br>
                    <label for='unitPriceOfEstimateBox' style='font-size: 20px; margin-right: 10px'>????????????</label>
                    <input type='text' id='unitPriceOfEstimateBox' autocomplete="off"/><br>
                    <label for='sumPriceOfEstimateBox' style='font-size: 20px; margin-right: 30px'>?????????  </label>
                    <input type="text" id='sumPriceOfEstimateBox' autocomplete="off"></input>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id ="amountSave" class="btn btn-default" data-dismiss="modal">Save</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
    <script>
        const estimateSearchBtn = document.querySelector("#estimateSearchButton");				//????????????
        const estimateDeleteBtn = document.querySelector("#estimateDeleteButton");				//????????????
        const myGrid = document.querySelector("#myGrid");
        const myGrid2 = document.querySelector("#myGrid2");
        const fromDate = document.querySelector("#fromDate");
        const toDate = document.querySelector("#toDate");
        const pdfOpenBtn = document.querySelector("#pdfOpenButton");							//O pdf??????
        const estimateDetaillBtn = document.querySelector("#estimateDetaillButton");			//O ??????????????????
        const datepicker = document.querySelector('#datepicker');
        const customerList = document.querySelector('#customerList');
        const itemList = document.querySelector('#itemList');
        const unitList = document.querySelector('#unitList');
        const amountList = document.querySelector('#amountList');
        const batchSaveButton = document.querySelector("#batchSaveButton");
        const excelOpenBtn = document.querySelector("#excelOpenButton");
        const pdfsendBtn = document.querySelector("#pdfsend");

      
      // O DATEPICKER => dbClick ?????? ??? ??? ?????????
    function getDatePicker(paramFmt) {
        let _this = this;
        _this.fmt = "yyyy-mm-dd";
        console.log(_this);

        // function to act as a class
        function Datepicker() {
        }

        // gets called once before the renderer is used
        Datepicker.prototype.init = function (params) {
            // create the cell
            this.autoclose = true;
            this.eInput = document.createElement('input');
            this.eInput.style.width = "100%";
            this.eInput.style.border = "0px";
            this.cell = params.eGridCell;
            this.oldWidth = this.cell.style.width;
            this.cell.style.width = this.cell.previousElementSibling.style.width;
            this.eInput.value = params.value;
            // Setting startDate and endDate
            console.log(paramFmt);
            let _startDate = datepicker.value;
            let _endDate = new Date(_startDate);
            let days = 14; // ?????? ????????? ???????????? + 14???
            if (paramFmt == "dueDateOfEstimate") {
                _startDate = new Date(_startDate)
                days = 9;
                _startDate.setTime(_startDate.getTime() + days * 86400000);
                let dd = _startDate.getDate();
                let mm = _startDate.getMonth() + 1; //January is 0!
                let yyyy = _startDate.getFullYear();
                _startDate = yyyy + '-' + mm + '-' + dd;
                console.log(_startDate);
                _endDate = new Date(_startDate);
                days = 19;
            }
            _endDate.setTime(_endDate.getTime() + days * 86400000);
            let dd = _endDate.getDate();
            let mm = _endDate.getMonth() + 1; //January is 0!
            let yyyy = _endDate.getFullYear();
            _endDate = yyyy + '-' + mm + '-' + dd;

            $(this.eInput).datepicker({
                dateFormat: _this.fmt,
                startDate: _startDate,
                endDate: _endDate,
            }).on('change', function () {
                estGridOptions.api.stopEditing();
                estDetailGridOptions.api.stopEditing();
                $(".datepicker-container").hide();
            });
        };
        // gets called once when grid ready to insert the element
        Datepicker.prototype.getGui = function () {
            return this.eInput;
        };

        // focus and select can be done after the gui is attached
        Datepicker.prototype.afterGuiAttached = function () {
            this.eInput.focus();
            console.log(this.eInput.value);
        };

        // returns the new value after editing
        Datepicker.prototype.getValue = function () {
            console.log(this.eInput);
            return this.eInput.value;
        };

        // any cleanup we need to be done here
        Datepicker.prototype.destroy = function () {
            estGridOptions.api.stopEditing();
        };

        return Datepicker;
    }
 // o if customer modal hide, next cell
    $("#customerModal").on('hide.bs.modal', function () {
        estGridOptions.api.stopEditing();
        let rowNode = estGridOptions.api.getRowNode(datepicker.value);
        console.log("rowNode:" + rowNode);
        if (rowNode != undefined) { // rowNode??? ????????? ????????? ?????? ????????? ??????
            setDataOnCustomerName();
        }
    });

    // o change customerName cell
    function setDataOnCustomerName() {
        let rowNode = estGridOptions.api.getRowNode(datepicker.value);
        let to = transferVar();
        console.log(to);
        rowNode.setDataValue("customerName", to.detailCodeName);
        rowNode.setDataValue("customerCode", to.detailCode);
        let newData = rowNode.data;
        rowNode.setData(newData);
        console.log(rowNode.data);
    }
      
    
  

        const estimuloInfoColumn = [
        
        	{
                headerName: "??????????????????", field: "estimateNo", suppressSizeToFit: true, headerCheckboxSelection: false,
                headerCheckboxSelectionFilteredOnly: true, suppressRowClickSelection: true,
                checkboxSelection: true
            },
            { headerName: "???????????????", field: "customerCode", width: 400 },
            { headerName: '????????????', width: 450, field: 'estimateDate' },
            { headerName: '????????????', field: 'contractStatus', hide: true },
            { headerName: '???????????????', field: 'personNameCharge', hide: true },
            { headerName: '????????????', field: 'effectiveDate', width: 350 },
            { headerName: '?????????????????????', field: 'personCodeInCharge', width: 350 },
            { headerName: '??????', field: 'description' },
        ];
      
        
        let rowData = [];
        let estimuloInfoRowNode;
        let estimuloInfoGridOptions = {
            columnDefs: estimuloInfoColumn,
            rowSelection: 'single',
            rowData: rowData,
            getRowNodeId: function (data) {
                return data.estimateNo;
            },
            defaultColDef: {editable: false, resizable : true},
            overlayNoRowsTemplate: "????????? ?????? ???????????? ????????????.",
            onGridReady: function (event) {// onload ???????????? ?????? ready ?????? ????????? ????????? ????????????.
                event.api.sizeColumnsToFit();
            },
            onGridSizeChanged: function (event) {
                event.api.sizeColumnsToFit();
            },
            onCellEditingStarted: (event) => {
                if (event != undefined) {
                    console.log("IN onRowSelected");
                    let rowNode = estimuloInfoGridOptions.api.getDisplayedRowAtIndex(event.rowIndex);
                    console.log(rowNode);
                    estimuloInfoRowNode = rowNode;
          
                } 
            },
            getSelectedRowData() { 
                let selectedNodes = this.api.getSelectedNodes();
                let selectedData = selectedNodes.map(node => node.data);
                console.log(selectedNodes+"selectedNodes");
                console.log(selectedData+"selectedData");
                return selectedData;
            }
        }
        
        //????????????
        estimateSearchBtn.addEventListener("click", () => {
        	console.log("test~~~");
            let isChecked = document.querySelector("#estimateDateRadio").checked;
            let dateApply = isChecked ? "estimateDate" : "effectiveDate";
            console.log(dateApply);
            estimuloInfoGridOptions.api.setRowData([]);
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/sales/searchEstimate.do' +
                "?method=searchEstimateInfo"
                + "&startDate=" + fromDate.value
                + "&endDate=" + toDate.value
                + "&dateSearchCondition=" + dateApply,
                true)
                console.log(fromDate.value);
                console.log(toDate.value);
            xhr.setRequestHeader('Accept', 'application/json');
            xhr.send();
            xhr.onreadystatechange = () => {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    let txt = xhr.responseText;
                    txt = JSON.parse(txt);
                    console.log(txt.gridRowJson);
                    let gridRowJson = txt.gridRowJson;  // gridRowJson : ???????????? ?????? json ????????? data
                    if (gridRowJson == "") {
                        swal({
                        	icon: 'error',
                        	text : "????????? ????????? ????????????."
                        });
                        return;
                    }
                    estimuloInfoGridOptions.api.setRowData(txt.gridRowJson);
                }
            }
        });
       
        //????????????
        estimateDeleteBtn.addEventListener("click", () => {
        	let selectedNodes = estimuloInfoGridOptions.api.getSelectedNodes();
        	console.log(selectedNodes);
        	 if (selectedNodes == "") {
        	        Swal({
        	          position: "top",
        	          icon: 'error',
        	          title: '?????? ??????',
        	          text: '????????? ?????? ????????????.',
        	        })
        	        return;
        	        }
        	 let estimateNo = selectedNodes[0].id;
        	 console.log(estimateNo);
        	 Swal.fire({
        	      title: "????????? ?????????????????????????",
        	      icon: 'warning',
        	      showCancelButton: true,
        	      confirmButtonColor: '#3085d6',
        	      cancelButtonColor: '#d33',
        	      cancelButtonText: '??????',
        	      confirmButtonText: '??????',
        	    }).then( (result) => {
        	      if (result.isConfirmed) {
        	      let xhr = new XMLHttpRequest();
        	      xhr.open('POST', '${pageContext.request.contextPath}/sales/deleteEstimate.do' +
        	                "?method=deleteEstimate"
        	                + "&estimateNo=" + estimateNo,
        	                true);
        	      xhr.setRequestHeader('Accept', 'application/json');
        	      xhr.send();
        	      xhr.onreadystatechange = () => {
        	        if (xhr.readyState == 4 && xhr.status == 200) {
        	          let txt = xhr.responseText;
        	          txt = JSON.parse(txt);
        	          let resultMsg =
        	              "<h5>< ?????? ?????? ?????? ></h5>"
        	              + "???????????? : <b>" + txt.result.EstimateNo + "</b></br>"
        	              + "?????? ?????? ????????? ?????????????????????";
        	          Swal.fire({
        	            title: "??????????????? ?????????????????????.",
        	            html: resultMsg,
        	            icon: "success",
        	          });
        	        }
        	      };
        	    }})
           
        });
        
      //PDF iReport  
        pdfOpenBtn.addEventListener("click", () => {
        	let selectedNodes = estimuloInfoGridOptions.api.getSelectedNodes();
        	console.log(selectedNodes);
        	 if (selectedNodes == "") {
        	        Swal({
        	          position: "top",
        	          icon: 'error',
        	          title: '?????? ??????',
        	          text: '????????? ?????? ????????????.',
        	        })
        	        return;
        	        }
        	 let getRowIdValue = selectedNodes[0].id;
        	 console.log(getRowIdValue);
        	window.open("${pageContext.request.contextPath}/base/report.pdf?method=estimateReport&orderDraftNo="+getRowIdValue,"window", "width=1600, height=1000")
        });

		//EXCEL
		excelOpenBtn.addEventListener("click",()=>{
			
			let selectedRows = estimuloInfoGridOptions.api.getSelectedRows();
			console.log(selectedRows[0]);
        	if (selectedRows == "") {
        	    Swal({
        	      position: "top",
        	      icon: 'error',
        	      title: '?????? ??????',
        	      text: '????????? ?????? ????????????.',
        	    })
        	  return;
        	}
        	let getRowIdValue = selectedRows[0].estimateNo;
        	console.log(getRowIdValue);
        	var $preparingFileModal = $("#preparing-file-modal");
	        $preparingFileModal.dialog({ modal: true });
	        setTimeout(function() {
	        	$("#progressbar").progressbar({value: false});
		        $.fileDownload("${pageContext.request.contextPath}/base/downloadExcel.do?method=downloadEstimateExcel&target=estimate&data="+getRowIdValue, {
		            successCallback: function (url) {
		            	setTimeout(function(){
			                $preparingFileModal.dialog('close');
		            	},1000)
		            },
		            failCallback: function (responseHtml, url) {
		                $preparingFileModal.dialog('close');
		                $("#error-modal").dialog({ modal: true });
		            }
		        });
	        }, 1000);
	        
	        // ????????? ?????? ?????? ???????????? ?????? ????????? ?????? ???????????????.
	       /* return false;
        	 $.ajax({
        		url:"${pageContext.request.contextPath}/base/downloadExcel.do",
        		data:{
        			method:"downloadEstimateExcel",
        			target:"estimate",
        			data:getRowIdValue
        		},
        		success:function(result){
        			{
        				Swal.fire({
            	            title: "??????",
            	            html: '??????????????? ???????????????.',
            	            icon: "success",
            	          })
        			}
        		}
        	}) */
		})
        
        // ???????????? grid
        const estimateDetailGridColumn = [
        
        	{
                headerName: "????????????????????????", field: "estimateDetailNo", suppressSizeToFit: true, headerCheckboxSelection: false,
                headerCheckboxSelectionFilteredOnly: true, suppressRowClickSelection: true,
                checkboxSelection: true
            },
            { headerName: "????????????", field: "itemCode", width: 400 },
            { headerName: '?????????', width: 450, field: 'itemName' },
            { headerName: '??????', field: 'unitPriceOfEstimate', hide: true },
            { headerName: '?????????', field: 'dueDateOfEstimate', hide: true },
            { headerName: '????????????', field: 'estimateAmount', width: 350 },
            { headerName: '????????????', field: 'unitOfEstimate', width: 350 },
            { headerName: '?????????', field: 'sumPriceOfEstimate' },
            { headerName: '??????', field: 'description' },
            { headerName: 'STATUS', field: 'status' },
            { headerName: '??????????????????', field: 'estimateNo' },
        ];   
        let itemRowNode;
        let detailRowData = [];
        let estimateDetailInfoGridOptions = {
            columnDefs: estimateDetailGridColumn,
            rowSelection: 'single',
            rowData: detailRowData,
            getRowNodeId: function (data) {
                return data.estimateDetailNo;
            },
            defaultColDef: {editable: false, resizable : true},
            overlayNoRowsTemplate: "????????? ?????? ???????????? ????????????.",
            onGridReady: function (event) {// onload ???????????? ?????? ready ?????? ????????? ????????? ????????????.
                event.api.sizeColumnsToFit();
            },
            onGridSizeChanged: function (event) {
                event.api.sizeColumnsToFit();
            },
            onCellEditingStarted: (event) => {
                if (event != undefined) {
                    console.log("IN onRowSelected");
                    let rowNode = estimuloInfoGridOptions.api.getDisplayedRowAtIndex(event.rowIndex);
                    console.log(rowNode);
                    itemRowNode = rowNode;
                }
            },
            getSelectedRowData() { 
                let selectedNodes = this.api.getSelectedNodes();
                let selectedData = selectedNodes.map(node => node.data);
                console.log(selectedNodes+"selectedNodes");
                console.log(selectedData+"selectedData");
                return selectedData;
            },
            onCellDoubleClicked: (event) => {
            	if (event != undefined) {
                    console.log("IN onRowSelected");
                    let rowNode = estimateDetailInfoGridOptions.api.getDisplayedRowAtIndex(event.rowIndex);
                    console.log(rowNode);
                    itemRowNode = rowNode;
                }
                if (event.colDef.field == "itemCode" || event.colDef.field == "itemName") {
                    itemList.click();
                } else if (event.colDef.field == "unitOfEstimate") {
                    unitList.click();
                } else if (event.colDef.field == "estimateAmount") {
                    amountList.click();
                }
            },
            onRowSelected: function (event) { // checkbox
                console.log(event);
            },
            onSelectionChanged(event) {
                console.log("onSelectionChanged" + event);
            },
          
        }
        
        
        
        //??????????????????
        estimateDetaillBtn.addEventListener("click", () => {
        	console.log("test~~~");
        	let selectedNodes = estimuloInfoGridOptions.api.getSelectedNodes();
        	let getRowIdValue = selectedNodes[0].id;
        	
        	estimateDetailInfoGridOptions.api.setRowData([]);
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/sales/batchEstimateDetailListProcess.do' +
                "?method=searchEstimateDetailInfo"
                + "&estimateNo=" + getRowIdValue,
                true)
            xhr.setRequestHeader('Accept', 'application/json');
            xhr.send();
            xhr.onreadystatechange = () => {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    let txt = xhr.responseText;
                    txt = JSON.parse(txt);
                    console.log(txt.gridRowJson);
                    let gridRowJson = txt.gridRowJson;  // gridRowJson : ???????????? ?????? json ????????? data
                    if (gridRowJson == "") {
                        swal({
                           position: "top",
              	          icon: 'error',
              	          title: '?????? ??????',
              	          text: '????????? ??????????????? ????????????.',
                        });
                        return;
                    }
                    estimateDetailInfoGridOptions.api.setRowData(txt.gridRowJson);
                }
            }
        })
        
     
    // O add and Delete function
    function addRow(event) {
        if (event.innerText == "??????????????????") {
        	let selectRowDate = estimuloInfoGridOptions.api.getSelectedRows();
        	console.log(selectRowDate);
        	let contractStatus = estimuloInfoGridOptions.api.getSelectedRows()[0].contractStatus;
        	
        	if(contractStatus != null){
        	 let errorMsg = (contractStatus == 'Y') ? "?????? ????????? ???????????????." : "?????? ????????? ???????????????."
        		Swal({
      	          position: "top",
      	          icon: 'error',
      	          title: '????????? ??????',
      	          text: errorMsg,
      	        })
                 return;
            } 
        	
        	let newRow = {
    				estimateDetailNo : '????????? ?????? ???',
    				itemCode : '',
    				itemName : '',
    				unitPriceOfEstimate : '',
    				dueDateOfEstimate : '',
    				estimateAmount : '',
    				unitOfEstimate : '',
    				sumPriceOfEstimate : '',
    				description : '',
    				status : 'INSERT',
    				estimateNo : selectRowDate[0].estimateNo
    	    };
            estimateDetailInfoGridOptions.api.updateRowData({add: [newRow]});
        } 
    }

    function deleteRow(event) {
        let selected = estimateDetailInfoGridOptions.api.getFocusedCell();                   
        if (selected == undefined) {
            swal({
                text: "????????? ?????? ????????????.",
                icon: "info",
            })
            return;
        }
        if (event.innerText == "???????????????????????????"){
                console.log("??????????????????");
                let selectedRows   = estimateDetailInfoGridOptions.api.getSelectedRows();
                console.log("????????? ???" + selectedRows );
                selectedRows.forEach( function(selectedRow, index) {
                    console.log(selectedRow);
                    detailItemCode.splice(detailItemCode.indexOf(selectedRow.itemCode), 1); // ???????????? ??????
                    estimateDetailInfoGridOptions.api.updateRowData({remove: [selectedRow]});
                });
        }
    }

    // ???????????? <= ????????? ????????? ??????
    batchSaveButton.addEventListener("click", () => {
    	console.log("????????????");
    	let newEstimateRowValue = estimuloInfoGridOptions.getSelectedRowData();
    	let selectRowDate = estimateDetailInfoGridOptions.api.getSelectedRows();
    	
    	console.log(selectRowDate);
    	let contractStatus = estimuloInfoGridOptions.api.getSelectedRows()[0].contractStatus;
    	
    	if(contractStatus != null){
    	 let errorMsg = (contractStatus == 'Y') ? "?????? ????????? ???????????????." : "?????? ????????? ???????????????."
    		Swal({
  	          position: "top",
  	          icon: 'error',
  	          title: '????????? ??????',
  	          text: errorMsg,
  	        })
             return;
        }
        
      
        newEstimateRowValue = estimuloInfoGridOptions.getSelectedRowData()[0];
        console.log(newEstimateRowValue);

        selectedRows.forEach( function(selectedRow, index) {
            // selectedRow {...}
            if (selectedRow.itemCode == "" || selectedRow.unitOfEstimate == "" || selectedRow.dueDateOfEstimate == ""
                || selectedRow.estimateAmount == "") {
                swal({
                    text: "??????????????? ????????????, ??????, ?????????, ??????????????? ?????????????????????.",
                    icon: "info",
                })
                return;
            }
        });
        console.log("newEstimateRowValue")
        selectedRows = JSON.stringify(newEstimateRowValue);
        console.log(newEstimateRowValue)
        swal({
            title: "??????????????? ?????????????????????????",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "?????? ??????!",
            closeOnConfirm: false
        }).then( (isConfirm) => {
            if (!isConfirm) {return;}
            let xhr = new XMLHttpRequest();
            xhr.open('POST', "/sales/batchEstimateDetailListProcess.do?method=batchListProcess&batchList=" + encodeURI(newEstimateRowValue),
                true);
            xhr.setRequestHeader('Accept', 'application/json');
            xhr.send();
            xhr.onreadystatechange = () => {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // ?????????
                    let txt = xhr.responseText;
                    txt = JSON.parse(txt);
                    let resultMsg =
                        "< ?????? ?????? ?????? >   <br/><br/>"
                        + "????????? ?????????????????? : " + txt.result.estimateDetailNo + "</br></br>"
                        + "?????? ?????? ????????? ?????????????????????";
                    swal({
                        title: "??????????????????????????? ?????????????????????.",
                        text: "??????????????????" + txt.result.estimateDetailNo,
                        type: "success",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes, delete it!",
                        closeOnConfirm: false
                    });
                }
            };
        })
    })
    
    pdfsendBtn.addEventListener("click", () => {
    	console.log("????????? ??????");
    	swal({
            title: "???????????? ?????? ???????????????????",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "?????? ??????!",
            closeOnConfirm: false
        }).then( (isConfirm) => {
            if (!isConfirm) {return;}
            let xhr = new XMLHttpRequest();
            xhr.open('POST', "/base/handleRequestInternal.do?method=handleRequestInternal",
                true);
            xhr.setRequestHeader('Accept', 'application/json');
            xhr.send();
            xhr.onreadystatechange = () => {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // ?????????
                   // let txt = xhr.responseText;
                    //txt = JSON.parse(txt);
                    swal({
                        title: "????????? ?????????????????????.",
                        type: "success",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes, delete it!",
                        closeOnConfirm: false
                    });
                }
            };
        })
	})
    
         // o if Item modal hide, next cell
    $("#itemModal").on('hide.bs.modal', function () {
        if (itemRowNode != undefined) { // rowNode??? ????????? ????????? ?????? ????????? ??????
            setDataOnItemName();
        }
    });
    let detailItemCode = [];
    function setDataOnItemName() {
    	estimateDetailInfoGridOptions.api.stopEditing();
        let to = transferVar();
        console.log(to);
        if (!detailItemCode.includes(to.detailCode)) {
            detailItemCode.push(to.detailCode);
            console.log("detailItemCode:" + detailItemCode);
        } else {
            swal({
                text: "???????????? ???????????????.",
                icon: "info",
            });
            return;
        }
        itemRowNode.setDataValue("itemCode", to.detailCode);
        itemRowNode.setDataValue("itemName", to.detailCodeName);
        let newData = itemRowNode.data;
        itemRowNode.setData(newData);
        console.log(itemRowNode.data);
    }

    $("#amountModal").on('show.bs.modal', function () {
        $('#estimateAmountBox').val("");
        $('#sumPriceOfEstimateBox').val("");
        $('#estimateAmountBox, #unitPriceOfEstimateBox').on("keyup", function() {
            let sum = $('#estimateAmountBox').val() * $('#unitPriceOfEstimateBox').val();
            $('#sumPriceOfEstimateBox').val(sum);
        });
    });
    document.querySelector("#amountSave").addEventListener("click", () => {
        if (itemRowNode == undefined) {return;}
        estimateDetailInfoGridOptions.api.stopEditing();
        itemRowNode.setDataValue("estimateAmount", $('#estimateAmountBox').val());
        itemRowNode.setDataValue("unitPriceOfEstimate", $('#unitPriceOfEstimateBox').val());
        itemRowNode.setDataValue("sumPriceOfEstimate", $('#sumPriceOfEstimateBox').val());
        let newData = itemRowNode.data;
        itemRowNode.setData(newData);
    })
    // O Button Click evenet
	     
        /* estimateDetailInsertBtn.addEventListener("click", () => {
        	let selectRowDate = estimuloInfoGridOptions.api.getSelectedRows();
        	console.log(selectRowDate);
        	let contractStatus = estimuloInfoGridOptions.api.getSelectedRows()[0].contractStatus;
        	
        	if(contractStatus != null){
        	 let errorMsg = (contractStatus == 'Y') ? "?????? ????????? ???????????????." : "?????? ????????? ???????????????."
        		Swal.fire({
      	          position: "top",
      	          icon: 'error',
      	          title: '????????? ??????',
      	          text: errorMsg,
      	        })
                 return;
        	}
        	if(selectRowDate != null){
        		let newRow = {
        				estimateDetailNo : '????????? ?????? ???',
        				itemCode : '',
        				itemName : null,
        				unitPriceOfEstimate : '',
        				dueDateOfEstimate : '',
        				estimateAmount : '',
        				unitOfEstimate : null,
        				sumPriceOfEstimate : null,
        				description : null,
        				status : null,
        				estimateNo : ''
        	    }
        		estimateDetailInfoGridOptions.api.updateRowData({add: [newRow]});
        	}
        }) */
       
        document.addEventListener('DOMContentLoaded', () => {
            new agGrid.Grid(myGrid, estimuloInfoGridOptions);
            new agGrid.Grid(myGrid2, estimateDetailInfoGridOptions);
        });

    </script>
</body>

</html>