<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>assign Authority Group</title>
    <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
    <style>
        * {
            margin: 0px;
        }

        h5 {
            margin-top: 3px;
            margin-bottom: 3px;
        }

        .ag-header-cell-label {
            justify-content: center;
        }
        .ag-cell-value {
            padding-left: 50px;
        }

        .menuButton button {
            background-color: #506FA9;
            border: none;
            color: white;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            border-radius: 3px;
        }

    </style>
</head>
<body>
<h4>๐ซ๊ถํ๊ทธ๋ฃน์ค์ </h4>
<h6>๋ฑ๋กํ  ๊ถํ๊ทธ๋ฃน์ <span style='color:red'>๋ชจ๋</span> ์ ํํ์ธ์</h6>
<table>
<tr>
<td> 
<article class="UserInformationGrid">
    <h5>๐ฑ์ฌ์ฉ์</h5>
    <div align="center">
        <div id="myGrid1" class="ag-theme-balham" style="height:500px; width:500px; text-align: center;"></div>
    </div>
</article>
</td>
<td>
	<div class="menuButton">
     	<button id="saveButton" style="float:right; background-color:#F15F5F";> &nbsp;์ ์ฅ&nbsp; </button>
	</div>
<article class="AuthorityGroupGrid">
	<h5>โ๏ธ๊ถํ๊ทธ๋ฃน</h5>
    <div align="center">
        <div id="myGrid2" class="ag-theme-balham" style="height:500px; width:500px; text-align: center;"></div>
    </div>
</article>
</td>
</tr>
</table>

<script>
   const myGrid1 = document.querySelector('#myGrid1');
   const myGrid2 = document.querySelector('#myGrid2');
   //์ฌ์ฉ์ ๊ทธ๋ฆฌ๋
   let userInformationColumn = [
       {headerName: "์ฌ์๋ช", field: "empName", checkboxSelection: true},
       {headerName: "์ง์ฑ", field: "positionName"},
       {headerName: "๋ถ์", field: "deptName"}
     ];

   let userInformationRowData = [];
   let userInformationGridOptions = {
             columnDefs: userInformationColumn,
             rowSelection: 'single',
             rowData: userInformationRowData,
             defaultColDef: {editable: false},
             overlayNoRowsTemplate: "์ฌ์ฉ์ ์ ๋ณด๊ฐ ์์ต๋๋ค.",
             onGridReady: function (event) {
               event.api.sizeColumnsToFit();
             },
             onGridSizeChanged: function (event) {
               event.api.sizeColumnsToFit();
             },
             onRowClicked: function(event) {
                getUserAuthorityGroupData();
               }
           }



   //๊ถํ๊ทธ๋ฃน ๊ทธ๋ฆฌ๋
   let authorityGroupColumn = [
       {headerName: "๊ถํ๊ทธ๋ฃน๋ช", field: "userAuthorityGroupName", checkboxSelection: true, headerCheckboxSelection: true, headerCheckboxSelectionFilteredOnly: true},
       {headerName: "๊ถํ๊ทธ๋ฃน์ฝ๋", field: "userAuthorityGroupCode", hide:true},
       {headerName: "ํ์ฌ ๊ถํ์ฌ๋ถ", field: "authority", cellRenderer: function (params) {
            if (params.value == "1") {
               // params.node.setSelected(true);
                return params.value =  "๐ข" ;
            }
            return 'โ๏ธ' ;
        }}
     ];
   let authorityGroupRowData = [];
   let authorityGroupGridOptions = {
             columnDefs: authorityGroupColumn,
             rowSelection: 'multiple',
             rowData: authorityGroupRowData,
             defaultColDef: {editable: false},
             overlayNoRowsTemplate: "๊ถํ๊ทธ๋ฃน ์ ๋ณด๊ฐ ์์ต๋๋ค.",
             rowMultiSelectWithClick: true,
             onGridReady: function (event) {
               event.api.sizeColumnsToFit();
             },
             onGridSizeChanged: function (event) {
               event.api.sizeColumnsToFit();
             }
   }

   let EmployeeData;
   function getEmployeeData(){
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/affair/searchAllEmpList.do' +
            "?method=searchAllEmpList"
            + "&searchCondition=ALL"
            + "&companyCode=COM-01",
            true);
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let txt = xhr.responseText;
                EmployeeData = JSON.parse(txt).gridRowJson;
                console.log(EmployeeData);
                if (txt.errorCode < 0) {
                    Swal.fire({
                        text: '๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฌ๋ค์ด๋๋ฐ ์ค๋ฅ๊ฐ ๋ฐ์ํ์ต๋๋ค.',
                        icon: 'error',
                    });
                    return;
                }
            }
        }
   }

   let authorityGroupData;
   let employeeRowNode;
   let empCode;
   function getUserAuthorityGroupData(){
      employeeRowNode = userInformationGridOptions.api.getSelectedNodes();
      empCode = employeeRowNode[0].data.empCode;
      console.log(empCode);
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/authoritymanager/getUserAuthorityGroup.do' +
            "?method=getUserAuthorityGroup&empCode="+empCode,
            true);
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let txt = xhr.responseText;
                authorityGroupData = JSON.parse(txt).gridRowJson;
                // console.log(authorityGroupData);
                authorityGroupGridOptions.api.setRowData(authorityGroupData);
                if (txt.errorCode < 0) {
                    Swal.fire({
                        text: '๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฌ๋ค์ด๋๋ฐ ์ค๋ฅ๊ฐ ๋ฐ์ํ์ต๋๋ค.',
                        icon: 'error',
                    });
                    return;
                }
            }
        }
   }

     //์ ์ฅ๋ฒํผ ํด๋ฆญ์ด๋ฒคํธ
     saveButton.addEventListener("click", () => {
           Swal.fire({
               title: '๊ถํ๊ทธ๋ฃน ๋ณ๊ฒฝ',
               text: "๊ถํ๊ทธ๋ฃน์ ๋ณ๊ฒฝํ์๊ฒ ์ต๋๊น?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               cancelButtonText: '์ทจ์',
               confirmButtonText: 'ํ์ธ',
             }).then((result) => {
                if (result.isConfirmed) {
                 let employeeAuthorityGroupData = [];
               let authorityGroupData = authorityGroupGridOptions.api.getSelectedNodes();
               for(data of authorityGroupData){
                  let newData = {empCode:empCode, authorityGroupCode: data.data.userAuthorityGroupCode};
                  employeeAuthorityGroupData.push(newData);
               }
               let insertData = JSON.stringify(employeeAuthorityGroupData);
               // console.log(insertData);

                   let xhr = new XMLHttpRequest();
                 xhr.open('POST', '${pageContext.request.contextPath}/authoritymanager/insertEmployeeAuthorityGroup.do' +
                     "?method=insertEmployeeAuthorityGroup"+
                     "&empCode="+empCode+
                     "&insertData="+encodeURI(insertData),
                     true);
                 xhr.setRequestHeader('Accept', 'application/json');
                 xhr.send();
                 xhr.onreadystatechange = () => {
                     if (xhr.readyState == 4 && xhr.status == 200) {
                        let txt = xhr.responseText;
                        getUserAuthorityGroupData();
                        // ํ์ฌ ๋ก๊ทธ์ธ ํ user์ ์ ๋ณด๊ฐ ๋ณ๊ฒฝ๋์์ ๊ฒฝ์ฐ, session ์ฌ ์ธํ์ ์ํด ์ฌ๋ก๊ทธ์ธ์ด ํ์ํ๋ค.
                        if(empCode=='${sessionScope.empCode}'){
                            Swal.fire({
                               title: '๋ณ๊ฒฝ์ฌํญ ์ ์ฉ์ ์ํด์ ๋ค์ ๋ก๊ทธ์ธ์ด ํ์ํฉ๋๋ค.',
                               text: "์ง๊ธ ๋ค์ ๋ก๊ทธ์ธ ํ์๊ฒ ์ต๋๊น?",
                               icon: 'warning',
                               showCancelButton: true,
                               confirmButtonColor: '#3085d6',
                               cancelButtonColor: '#d33',
                               cancelButtonText: '์ทจ์',
                               confirmButtonText: 'ํ์ธ',
                             }).then((result) => {
                                if (result.isConfirmed) {
                                   location.href="${pageContext.request.contextPath}/authoritymanager/loginForm.html";
                                }
                             });
                        }else{
                           Swal.fire('๋ณ๊ฒฝ ์๋ฃ','๊ถํ๊ทธ๋ฃน์ด ์ ์์ ์ผ๋ก ๋ณ๊ฒฝ๋์์ต๋๋ค','success');
                        }
                         if (txt.errorCode < 0) {
                             Swal.fire({
                                 text: '๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฌ๋ค์ด๋๋ฐ ์ค๋ฅ๊ฐ ๋ฐ์ํ์ต๋๋ค.',
                                 icon: 'error',
                             });
                             return;
                         }
                     }
                 }
            }
      });
     });

     function setGrid(){
         new agGrid.Grid(myGrid1, userInformationGridOptions);
            userInformationGridOptions.api.setRowData(EmployeeData);
         new agGrid.Grid(myGrid2, authorityGroupGridOptions);
     }
     document.addEventListener('DOMContentLoaded', () => {
        getEmployeeData();
        setTimeout("setGrid()",200);
        });
</script>
</body>
</html>