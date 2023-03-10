<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>purchase</title>
    <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
    <script src="${pageContext.request.contextPath}/js/modal.js?v=<%=System.currentTimeMillis()%>" defer></script>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" defer></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.css">

    <script>
      // O setting datapicker
      $(function() {
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
        $('#fromDate').on("change", function() {
          //when chosen from_date, the end date can be from that point forward
          var startVal = $('#fromDate').val();
          $('#toDate').data('datepicker').setStartDate(startVal);
        });
        $('#toDate').on("change", function() {
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
            padding-left: 25px;
        }

        .form-control {
            display: inline;
        !important;
        }
        #orderModal {
            position: absolute !important;
            z-index: 3000;
        }
      .btnContainer{
         display:flex;
         flex-direction:row;
      }
      .checkBtn{
         margin-right:10px;
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
<article class="warehousing">
    <div class="warehousing__Title" style="color: black">
        <h5>??????? ??????</h5>
        <b>??????</b></br>
        <form autocomplete="off" style="display: inline-block">
            <input type="text" data-toggle="datepicker" id="warehousingDate" placeholder="?????? ?????? ????" size="15"
                   autocomplete="off" style="text-align: center">&nbsp&nbsp
        </form>
        <button id="warehousingModalButton">??????</button>
    </div>
</article>
<article class="myGrid">
    <div align="center">
        <div id="myGrid" class="ag-theme-balham" style="height:70vh; width:auto; text-align: center;"></div>
    </div>
</article>
<%--O WAREHOUSING MODAL--%>
<div class="modal fade" id="warehousingModal" role="dialog">
    <div class="modal-dialog modal-xl">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h5>MRP SIMULATION</h5>
                    <div class="btnContainer">
                       <button id="checkingButton" class="checkBtn">???????????? ??????</button>
                       <button id="warehousingButton">???????????? ??????</button>
                    </div>
                </div>
                <button type="button" class="close" data-dismiss="modal" style="padding-top: 0.5px">&times;</button>
            </div>
            <div class="modal-body">
                <div id="warehousingGrid" class="ag-theme-balham" style="height: 40vh;width:auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script>
  const myGrid = document.querySelector("#myGrid");
  const warehousingModalBtn = document.querySelector("#warehousingModalButton");
  const warehousingDate = document.querySelector("#warehousingDate");
  const warehousingBtn = document.querySelector("#warehousingButton");
  const checkingBtn = document.querySelector("#checkingButton");
  
  const stockColumn = [
    {headerName: "????????????", field: "warehouseCode",},
    {headerName: "????????????", field: "itemCode",},
    {headerName: '?????????', field: "itemName",},
    {headerName: '??????', field: 'unitOfStock',},
    {headerName: '???????????????', field: 'totalStockAmount',},
    {headerName: '???????????????', field: 'safetyAllowanceAmount',},
    {headerName: '???????????????', field: 'stockAmount',},
    {headerName: '?????????????????????', field: 'orderAmount',},
    {headerName: '?????????????????????', field: 'inputAmount',},
    {headerName: '?????????????????????', field: 'deliveryAmount',}
  ];
  let stockRowData = [];
  const stockGridOptions = {
    defaultColDef: {
      flex: 1,
      minWidth: 100,
      resizable: true,
    },
    columnDefs: stockColumn,
    rowSelection: 'multiple',
    rowData: stockRowData,
    getRowNodeId: function(data) {
      return data.itemCode;
    },
    defaultColDef: {editable: false, resizable : true},
    overlayNoRowsTemplate: "????????? ?????? ???????????? ????????????.",
    onGridReady: function(event) {// onload ???????????? ?????? ready ?????? ????????? ????????? ????????????.
      event.api.sizeColumnsToFit();
    },
    onRowSelected: function(event) { // checkbox
      console.log(event);
    },
    onGridSizeChanged: function(event) {
      event.api.sizeColumnsToFit();
    },
    getRowStyle: function(param) {
      return {'text-align': 'center'};
    },
  }
  const showStockGrid = () => {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '${pageContext.request.contextPath}/material/searchStockList.do' +
        "?method=searchStockList",
        true)
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.send();
    xhr.onreadystatechange = () => {
      if (xhr.readyState == 4 && xhr.status == 200) {
        let txt = xhr.responseText;
        txt = JSON.parse(txt);
        if (txt.errorCode < 0) {
          swal.fire("??????", txt.errorMsg, "error");
          return;
        }
        console.log(txt);
        stockGridOptions.api.setRowData(txt.gridRowJson);
      }
    }
  }
  // O warehousing modal
  let _setWarehousingModal = (function() {
    let executed = false;
    return function() {
      if (!executed) {
        executed = true;
        setWarehousingModal();
      }
    };
  })();
  
  warehousingModalBtn.addEventListener("click", () => {
    if (warehousingDate.value == "") {
      Swal.fire("??????", "?????? ????????? ??????????????????.", "info");
      return;
    }
    _setWarehousingModal();
    getWarehousingModal();
    $("#warehousingModal").modal('show');
  });
  // o warehousing modal warehousing
  let orderNoList = [];
  checkingBtn.addEventListener("click",()=>{
     orderNoList = [];
     let checkedItem=false;
     let selectedRows = warehousingGridOptions.api.getSelectedRows();
       if (selectedRows == "") {
         Swal.fire("??????", "????????? ?????? ????????????.", "info");
         return;
       }
       selectedRows.forEach(function(selectedRow, index) {
        if(selectedRow.inspectionStatus=='Y'){
           checkedItem=true;
        }
         orderNoList.push(selectedRow.orderNo);
         console.log(selectedRow);
       });
       if(checkedItem==true){
          orderNoList=[];
          Swal.fire("??????", "????????? ????????? ????????? ????????????.", "info");
          return;
       }
       
       console.log(selectedRows);
       Swal.fire({
           title: '?????????????????????????',
           html: '????????????</br>'+ '<b>' + orderNoList + '</b>'+ '</br>???????????? ?????????.',
           icon: 'warning',
           showCancelButton: true,
           confirmButtonColor: '#3085d6',
           cancelButtonColor: '#d33',
           cancelButtonText: '??????',
           confirmButtonText: '??????',
         }).then((result)=>{
            if (result.isConfirmed) {
                  let xhr = new XMLHttpRequest();
                  xhr.open('POST', '${pageContext.request.contextPath}/material/checkOrderInfo.do' +
                      "?method=checkOrderInfo"
                      + "&orderNoList=" + encodeURI(JSON.stringify(orderNoList)),
                      true)
                  xhr.setRequestHeader('Accept', 'application/json');
                  xhr.send();
                  xhr.onreadystatechange = () => {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                      let txt = JSON.parse(xhr.responseText);
                      console.log(txt);
                      if (txt.errorCode < 0) {
                        swal.fire("??????", txt.errorMsg, "error");
                        return;
                      }
                      Swal.fire(
                          '??????!',
                          '???????????? ????????????.',
                          'success'
                      )
                      warehousingGridOptions.api.updateRowData({ update: selectedRows });
                      _setWarehousingModal();
                      getWarehousingModal();
                      $("#warehousingModal").modal('show');
                    }
                  }
                }
         })
  })
  warehousingBtn.addEventListener('click', () => {
    let selectedRows = warehousingGridOptions.api.getSelectedRows();
    if (selectedRows == "") {
      Swal.fire("??????", "????????? ?????? ????????????.", "info");
      return;
    }
    orderNoList=[];
    selectedRows.forEach(function(selectedRow, index) {
      orderNoList.push(selectedRow.orderNo);
      console.log(selectedRow);
    });
    Swal.fire({
      title: '?????????????????????????',
      html: '????????????</br>'+ '<b>' + orderNoList + '</b>'+ '</br>?????????????????????.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: '??????',
      confirmButtonText: '??????',
    }).then((result) => {
      if (result.isConfirmed) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/material/warehousing.do' +
            "?method=warehousing"
            + "&orderNoList=" + encodeURI(JSON.stringify(orderNoList)),
            true)
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
          if (xhr.readyState == 4 && xhr.status == 200) {
            let txt = JSON.parse(xhr.responseText);
            console.log(txt);
            if (txt.errorCode < 0) {
              swal.fire("??????", txt.errorMsg, "error");
              return;
            }
            Swal.fire(
                '??????!',
                '??????????????? ???????????? ???????????????.',
                'success'
            )
            selectedRows.forEach(function(selectedRow, index) {
              warehousingGridOptions.api.updateRowData({remove: [selectedRow]});
            });
            console.log(txt);
            showStockGrid();
          }
        }
      }
    })
  });
   
  document.addEventListener('DOMContentLoaded', () => {
    showStockGrid();
    new agGrid.Grid(myGrid, stockGridOptions);
  });
</script>
</body>
</html>