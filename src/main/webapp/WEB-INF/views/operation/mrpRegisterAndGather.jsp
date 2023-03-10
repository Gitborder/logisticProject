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
            display: inline; !important;
        }

        @media (min-width: 768px) {
            .modal-xl {
                width: 90%;
                max-width:1200px;
            }
        }
    </style>
</head>
<body>
<article class="contract">
    <div class="contract__Title">
        <h5>???? MRP ??????</h5>
        <div style="color: black">
            <b>MRP ???????????????</b></br>
            <label for="includeMrpApplyRadio">????????????</label>
            <input type="radio" name="includeMrpApplyCondition" value="includeMrpApply" id="includeMrpApplyRadio"
                   checked>
            &nbsp<label for="excludeMrpApplyRadio">???????????????</label>
            <input type="radio" name="includeMrpApplyCondition" value="excludeMrpApply" id="excludeMrpApplyRadio">
        </div>

        <form autocomplete="off">
            <div class="fromToDate">
                <input type="text" id="fromDate" placeholder="YYYY-MM-DD ????" size="15" style="text-align: center">
                &nbsp ~ &nbsp<input type="text" id="toDate" placeholder="YYYY-MM-DD ????" size="15"
                                    style="text-align: center">
            </div>
        </form>
        <button id="mpsSearchButton">MPS??????</button>
        &nbsp&nbsp<button id="showMrpSimulationButton" style="background-color:#F15F5F" >MRP????????????</button>
    </div>
</article>
<article class="mpsGrid">
    <div align="center">
        <div id="myGrid" class="ag-theme-balham" style="height:20vh; width:auto; text-align: center;"></div>
    </div>
</article>
<h5>??? ????????? ??????</h5>
<button id="showMrpGatheringButton">????????? ?????? ?????? ??????</button>
<article class="myGrid2">
    <div align="center">
        <div id="myGrid2" class="ag-theme-balham" style="height: 30vh;width:auto; text-align: center;"></div>
    </div>
</article>
<%--O MRP MODAL--%>
<div class="modal fade" id="mrpModal" role="dialog">
    <div class="modal-dialog modal-xl">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4>MRP SIMULATION</h4>
                    <input type="text" data-toggle="datepicker" id="mrpDate" placeholder="????????? ???????????? ????" size="15" autocomplete="off" style="text-align: center">&nbsp&nbsp
                    <button id="registerMrpButton">?????? ?????? MRP??????</button>
                </div>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div id="mrpGrid" class="ag-theme-balham" style="height: 40vh;width:auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--O MRP GATHERING MODAL--%>
<div class="modal fade" id="mrpGatheringModal" role="dialog">
    <div class="modal-dialog modal-xl">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4>MRP GATHERING SIMULATION</h4>
                    <input type="text" data-toggle="datepicker" id="mrpGatheringDate" placeholder="????????? ?????????????????? ????" size="20" autocomplete="off" style="text-align: center">&nbsp&nbsp
                    <button id="registerMrpGatheringButton">?????? ?????? ??????</button>
                </div>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div id="mrpGatheringGrid" class="ag-theme-balham" style="width:auto;height: 70vh">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script>
    const mpsSearchBtn = document.querySelector("#mpsSearchButton");
    const myGrid = document.querySelector("#myGrid");
    const myGrid2 = document.querySelector("#myGrid2");
    const fromDate = document.querySelector("#fromDate");
    const toDate = document.querySelector("#toDate");
    const mrpDate = document.querySelector("#mrpDate");
    const mrpGatheringDate = document.querySelector("#mrpGatheringDate");
    const showMrpSimulationBtn = document.querySelector("#showMrpSimulationButton");
    const registerMrpBtn = document.querySelector("#registerMrpButton");
    const showMrpGatheringBtn = document.querySelector("#showMrpGatheringButton");
    const registerMrpGatheringBtn = document.querySelector("#registerMrpGatheringButton");

    const mpsColumn = [
        {
            headerName: "?????????????????????", field: "mpsNo", suppressSizeToFit: true, headerCheckboxSelection: true,
            headerCheckboxSelectionFilteredOnly: true, suppressRowClickSelection: true,
            checkboxSelection: true
        },
        {headerName: "????????????", field: "mpsPlanClassification",width: 400},
        {headerName: '????????????(??????/??????)', width: 450, field: 'no'},
        {headerName: '????????????????????????', field: 'contractDetailNo', hide: true},
        {headerName: '????????????????????????', field: 'salesPlanNo', hide: true},
        {headerName: '????????????', field: 'itemCode', width: 350},
        {headerName: '?????????', field: 'itemName', width: 450},
        {headerName: '??????', field: 'unitOfMps'},
        {
            headerName: "????????????", field: "mpsPlanDate",width: 400, cellRenderer: function (params) {
                if (params.value == null) {
                    params.value = "";
                }
                return '???? ' + params.value;
            }
        },
        {
            headerName: "???????????????", field: "scheduledEndDate",width: 400, cellRenderer: function (params) {
                if (params.value == null) {
                    params.value = "";
                }
                return '???? ' + params.value;
            }
        },
        {headerName: '????????????', field: 'mpsPlanAmount',width: 300, cellStyle: {'textAlign': 'center'}},
        {headerName: '?????????', field: 'dueDateOfMps',width: 400, cellRenderer: function (params) {
                if (params.value == null) {
                    params.value = "";
                }
                return '???? ' + params.value;
            }},
        {headerName: '??????????????????', field: 'scheduledEndDate',width: 400},
        {headerName: 'MRP ????????????', field: 'mrpApplyStatus', cellRenderer:(params) => {
                if (params.value == 'Y') {
                    params.node.selectable = false;
                  return params.value = "????";
                }
                return '???';
            }},
        {headerName: '??????', field: 'description'},
    ];
    let rowData = [];
    let mpsRowNode = "";
    const mpsGridOptions = {
        defaultColDef: {
            flex: 1,
            minWidth: 100,
            resizable: true,
        },
        columnDefs: mpsColumn,
        rowSelection: 'multiple',
        rowData: rowData,
        getRowNodeId: function (data) {
            return data.contractDetailNo;
        },
        defaultColDef: {editable: false, resizable : true},
        overlayNoRowsTemplate: "????????? mps ???????????? ????????????.",
        onGridReady: function (event) {// onload ???????????? ?????? ready ?????? ????????? ????????? ????????????.
            event.api.sizeColumnsToFit();
        },
        onRowSelected: function (event) { // checkbox
        	mpsRowNode = this.api.getDisplayedRowAtIndex(event.rowIndex);
        },
        onGridSizeChanged: function (event) {
            event.api.sizeColumnsToFit();
        },
        getRowStyle: function (param) {
            return {'text-align': 'center'};
        },
    }
    
    mpsSearchBtn.addEventListener("click", () => {  // mps??????
        let xhr = new XMLHttpRequest();
        let isChecked = document.querySelector("#includeMrpApplyRadio").checked; 
        let mrpApply =  isChecked ? "includeMrpApply" : "excludeMrpApply";
        console.log(mrpApply);
        mpsGridOptions.api.setRowData([]);
        xhr.open('POST', '/operation/searchMpsInfo.do' +
            "?method=searchMpsInfo"
            + "&startDate=" + fromDate.value
            + "&endDate=" + toDate.value
            + "&includeMrpApply="+mrpApply,
            true)
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let txt = xhr.responseText;
                txt = JSON.parse(txt);
              
                let gridRowJson = txt.gridRowJson;  // gridRowJson : ???????????? ?????? json ????????? data
                if (gridRowJson == "") {
                    Swal.fire("??????", "mps ???????????? ????????? ????????????.", "info");
                    return;
                }
                
              	console.log(gridRowJson);
                gridRowJson.map((unit, idx) => {
                    unit.no = unit.mpsPlanClassification == "????????????" ?  unit.contractDetailNo : unit.salesPlanNo;
                   // if (mrpApply == "excludeMrpApply" && unit.mrpApplyStatus == null) {return false};
                    mpsGridOptions.api.updateRowData({add: [unit]});
                });
            }
        }
    });
    let _setMrpModal = (function() {
        let executed = false;
        return function() {
            if (!executed) {
                executed = true;
                setMrpModal();
            }
        };
    })();
    showMrpSimulationBtn.addEventListener("click", () => {  // mrp ???????????? 
        let selectedRows   = mpsGridOptions.api.getSelectedNodes();
    	console.log(mpsGridOptions.api.getSelectedNodes());
        if (selectedRows == "") {
            Swal.fire("??????", "??????????????? mps??? ??????????????????.", "info");
            return;
        }
        console.log(selectedRows);
        _setMrpModal();
        getMrpList(selectedRows);  // mpsRowNode : ????????? ?????? ?????? (????????????)
        $('#mrpModal').modal('show');
        mpsGridOptions.api.deselectAll(); // ?????? ??????
    });
    
    registerMrpBtn.addEventListener("click", () => {  // ???????????? MRP ?????? 
        if (mrpDate.value == "") {
            Swal.fire("??????", "?????? ?????? ????????? ??????????????????.", "info");
            return;
        }
        registerMrp(mrpDate.value);  // mrpDate : ????????????????????? 
    });
    
    $('#mrpModal').on('hidden.bs.modal', function (e) {
        getMrpSearchData();
    })


    //O ????????? ??????
    const mrpColumn = [
        {
            headerName: "?????????????????????", field: "mrpNo"
        },
        {headerName: "?????????????????????", field: "mpsNo"},
        {headerName: '????????????', field: 'itemClassification',},
        {headerName: '????????????', field: 'itemCode'},
        {headerName: '?????????', field: 'itemName'},
        {headerName: '??????/???????????? ??????', field: 'orderDate', cellRenderer: function (params) {
                if (params.value == null) {
                    params.value = "";
                }
                return '???? ' + params.value;
            }},
        {headerName: '??????/????????????????????????', field: 'requiredDate', cellRenderer: function (params) {
                if (params.value == null) {
                    params.value = "";
                }
                return '???? ' + params.value;
            }},
        {headerName: '????????????', field: 'requiredAmount'},
        {headerName: '??????', field: 'unitOfMrp'},
    ];
    // O ?????? ?????? ??????
    String.prototype.leftSpaceQuantity = function() {
        let count = 0;

        for(let i = 0; i < this.length; i++ ) {
            if( this.charAt(i) == ' ' )
                count++;
            else
                break;
        }

        return count;
    }

    let mrpData = [];
    const mrpSearchGridOptions = {
        defaultColDef: {
            flex: 1,
            minWidth: 100,
            resizable: true,
        },
        columnDefs: mrpColumn,
        rowSelection: 'single',
        rowData: mrpData,
        getRowNodeId: function (data) {
            return data.mrpNo;
        },
        defaultColDef: {editable: false, resizable : true},
        overlayNoRowsTemplate: "????????? mrp ???????????? ????????????.",
        onGridReady: function (event) {// onload ???????????? ?????? ready ?????? ????????? ????????? ????????????.
            event.api.sizeColumnsToFit();
        },
        onGridSizeChanged: function (event) {
            event.api.sizeColumnsToFit();
        },
        setAutoHeight: () => {
            mrpSearchGridOptions.api.setDomLayout('autoHeight');
            document.querySelector('#myGrid2').style.height = '';
        },
        getRowStyle: function (param) {
            if (param.data.itemName.leftSpaceQuantity() == 0) {
                return {'font-weight': 'bold', background: '#b1b5cc'};
            }else if (param.data.itemName.leftSpaceQuantity() == 8) {
                return {'text-align': 'center', background: '#c7cce5'};
            }else {
                return {'text-align': 'center', background: '#dee3ff'};
            }
        },
    }
    let mrpSearchData;
    const getMrpSearchData = () => {
        let xhr = new XMLHttpRequest(); 
        xhr.open('POST', '${pageContext.request.contextPath}/operation/getMrpList.do' +
            "?method=getMrpList"
            + "&mrpGatheringStatusCondition=null",   // ????????????????????? = null
            true)
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let txt = xhr.responseText;
                mrpSearchData = JSON.parse(txt);
                console.log(mrpSearchData);
                mrpSearchGridOptions.api.setRowData(mrpSearchData.gridRowJson);
                if (txt.errorCode < 0) {
                    Swal.fire({
                        text: '???????????? ?????????????????? ????????? ??????????????????.',
                        icon: 'error',
                    });
                    return;
                }
            }
        }
    }

    // O MRP GATHERING
    let _setMrpGatheringModal = (function() {
        let executed = false;
        return function() {
            if (!executed) {
                executed = true;
                setMrpGatheringModal()
            }
        };
    })();
    let mrpNoList = [];
    let mrpNoAndItemCodeList = {};
    showMrpGatheringBtn.addEventListener('click', () => { // ???????????????????????????
        _setMrpGatheringModal();  // ????????? ???????????? ?????? 
        for (let data of mrpSearchData.gridRowJson) {  // ????????????????????? ?????? ????????? 
            mrpNoList.push(data.mrpNo);
            mrpNoAndItemCodeList[data.mrpNo] = data.itemCode
        }
        console.log("mrpNoList"+mrpNoList);
        console.log("mrpNoAndItemCodeList"+mrpNoAndItemCodeList);
        getMrpGatheringModal(mrpNoList); // ????????????????????? ???????????? ????????? ???????????? ?????? 
        $('#mrpGatheringModal').modal('show');   
    });
    registerMrpGatheringBtn.addEventListener('click', () => { // ???????????? ?????? 
        if (mrpGatheringDate.value == "") {
            Swal.fire("??????", "????????? ??????????????? ??????????????????.", "info");
            return;
        }
        console.log("mrpNoList"+mrpNoList);
        registerMrpGathering(mrpGatheringDate.value, mrpNoList, mrpNoAndItemCodeList);
        $('#mrpGatheringModal').modal('hide');
    });
    
    $('#mrpGatheringModal').on('hidden.bs.modal', function () {
        getMrpSearchData();
    })

    document.addEventListener('DOMContentLoaded', () => {
        getMrpSearchData();
        new agGrid.Grid(myGrid, mpsGridOptions);
        new agGrid.Grid(myGrid2, mrpSearchGridOptions);
    });

</script>
</body>
</html>