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

    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
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
            <h4>???? ??????</h4>
            <div>
                <label for="searchByDateRadio">????????????</label>
                <input type="radio" name="searchCondition" value="searchByDate" id="searchByDateRadio" checked>
                &nbsp<label for="searchByDateDateRadio">????????? ??????</label>
                <input type="radio" name="searchCondition" value="searchByCustomer" id="searchByDateRadio">
            </div>

            <form autocomplete="off">
                <div class="fromToDate">
                    <input type="text" id="fromDate" placeholder="YYYY-MM-DD ????" size="15" style="text-align: center">
                    &nbsp ~ &nbsp<input type="text" id="toDate" placeholder="YYYY-MM-DD ????" size="15"
                        style="text-align: center">
                </div>
            </form>
            <button id="contractSearchButton">????????????</button>
            <button id="contractDetaillButton">???????????? ??????</button>
            <button id="pdfOpenButton">PDF ??????/??????</button>
        </div>
    </article>
    <article class="estimuloInfoGrid">
        <div align="center">
            <div id="myGrid" class="ag-theme-balham" style="height:30vh; width:auto; text-align: center;"></div>
        </div>
    </article>
    <br/>
    <article class="contractDetail">
    <div class="contractDetail__Title">
        <h4>???? ????????????</h4>
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
    
    
       
        
  //Grid ??????      
        const myGrid = document.querySelector("#myGrid");
        const myGrid2 = document.querySelector("#myGrid2");       
  //??????      
        const datepicker = document.querySelector('#datepicker'); //??????
        const fromDate = document.querySelector("#fromDate");
        const toDate = document.querySelector("#toDate");
  //??????      
        const contractSearchBtn = document.querySelector("#contractSearchButton");//??????????????????   
        const contractDetaillBtn = document.querySelector("#contractDetaillButton"); // ????????????????????????   
        const pdfOpenBtn = document.querySelector("#pdfOpenButton");            // pdf??????
      

      
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
      
    
  

        const contractInfoColumn = [
        
           {
                headerName: "??????????????????", field: "contractNo", suppressSizeToFit: true, headerCheckboxSelection: false,
                headerCheckboxSelectionFilteredOnly: true, suppressRowClickSelection: true,
                checkboxSelection: true
            },
            { headerName: "??????????????????", field: "estimateNo"},
            { headerName: '??????????????????', field: 'contractTypeName' },
            { headerName: '???????????????', field: 'customerCode', hide: true },
            { headerName: '????????????', field: 'customerName', hide: true },
            { headerName: '????????????', field: 'contractDate' },
            { headerName: '????????????', field: 'contractDate' },
            { headerName: '???????????????', field: 'contractRequester' },
            { headerName: '??????????????????', field: 'empNameInCharge' },
            { headerName: '??????????????????', field: 'deliveryCompletionStatus',   cellRenderer:(params) => {
                if (params.value == 'Y') {
                  return  "????";
                }
                return '???';
            }  },
            { headerName: '??????', field: 'description' },
        ];
      
        
        let rowData = [];
        let contractInfoColumnRowNode;
        let contractInfoGridOptions = {
            columnDefs: contractInfoColumn,
            rowSelection: 'single',
            rowData: rowData,
            getRowNodeId: function (data) {
                return data.contractNo;
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
                    let rowNode = contractInfoGridOptions.api.getDisplayedRowAtIndex(event.rowIndex);
                    console.log(rowNode);
                    contractInfoColumnRowNode = rowNode;
          
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
        
//-------------------------------------???????????? ??????--------------------------------------------//
     contractSearchBtn.addEventListener("click", () => {
            let isChecked = document.querySelector("#searchByDateRadio").checked;
            let dateApply = isChecked ? "searchByDate" : "searchByCustomer";
            contractInfoGridOptions.api.setRowData([]);
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/sales/searchContract.do' +
                "?method=searchContract"
                + "&startDate=" + fromDate.value
                + "&endDate=" + toDate.value
                /* + "&customerCode=" +  */
                + "&searchCondition=" + dateApply,
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
                    contractInfoGridOptions.api.setRowData(txt.gridRowJson);
                }
            }
        });

        
      //-------------------------------------???????????? ??????--------------------------------------------//        
        
        //??????????????????
        contractDetaillBtn.addEventListener("click", () => {
           let selectedNodes = contractInfoGridOptions.api.getSelectedNodes();
            if (selectedNodes == "") {
              Swal.fire({
                position: "top",
                icon: 'error',
                title: '?????? ??????',
                text: '????????? ?????? ????????????.',
              })
              return;
            }
            console.log(selectedNodes[0].data);
            getRowIdValue = selectedNodes[0].data.contractNo;
            
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/sales/searchContractDetail.do' +
                "?method=searchContractDetail"
                + "&contractNo=" + getRowIdValue,
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
                    contractDetailInfoGridOptions.api.setRowData(txt.gridRowJson);
                }
            }
        })        
        
        
        
        
 //-----------------------------------------PDF ?????? ????????????------------------------------------//      
        //PDF iReport  
        pdfOpenBtn.addEventListener("click", () => {
           let selectedNodes = contractInfoGridOptions.api.getSelectedNodes();
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
            console.log("test");
            console.log(getRowIdValue);
                    
           window.open("${pageContext.request.contextPath}/base/report.html?method=contractReport&orderDraftNo=" + getRowIdValue,"window", "width=1600, height=1000" );
        });
  
//-----------------------------------------???????????? ?????????------------------------------------//   
       
        // ???????????? grid           
        const contractDetailGridColumn = [
        
           {
                headerName: "????????????????????????", field: "contractDetailNo", width: 300, 
                suppressRowClickSelection: true
            },
            { headerName: '??????????????????', field: 'mrpGatheringNo',   cellRenderer:(params) => {
                if (params.value!=null) {
                  return params.value;
                }
                return '-';
            } },
            { headerName: '??????????????????', field: 'contractNo', hide: true },
            { headerName: "????????????", field: "itemCode", width: 300, hide: true },
            { headerName: '?????????', width: 300, field: 'itemName' },
            { headerName: '??????', field: 'unitOfContract', hide: true },
            { headerName: '?????????', field: 'dueDateOfContract'},
            { headerName: '????????????', field: 'estimateAmount' },
            { headerName: '???????????????', field: 'stockAmountUse' },
            { headerName: '??????????????????', field: 'productionRequirement' },
            { headerName: '????????????', field: 'unitPriceOfContract' },
            { headerName: '?????????', field: 'sumPriceOfContract' },
            { headerName: '??????????????????', field: 'operationCompletedStatus' ,   cellRenderer:(params) => {
                if (params.value == 'Y') {
                    params.node.selectable = false;
                  return params.value = "????";
                }
                return '???';
            }},
            { headerName: '??????????????????', field: 'deliveryCompletionStatus',   cellRenderer:(params) => {
                if (params.value == 'Y') {
                    params.node.selectable = false;
                  return params.value = "????";
                }
                return '???';
            } },
            { headerName: '??????', field: 'description' }
        ];   
        let itemRowNode;
        let detailRowData = [];
        let contractDetailInfoGridOptions = {
            columnDefs: contractDetailGridColumn,
            rowSelection: 'single',
            rowData: detailRowData,
            getRowNodeId: function (data) {
                return data.contractDetailNo;
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
            }
          
        }
       
        document.addEventListener('DOMContentLoaded', () => {
            new agGrid.Grid(myGrid, contractInfoGridOptions);
            new agGrid.Grid(myGrid2, contractDetailInfoGridOptions);
        });

    </script>
</body>

</html>