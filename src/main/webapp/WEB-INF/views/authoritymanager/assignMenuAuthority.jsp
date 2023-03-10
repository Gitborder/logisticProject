<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>assign Menu Authority</title>
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
<h4>๐ซ๋ฉ๋ด๊ถํ์ค์ </h4>
<h6>๋ฑ๋กํ  ๋ฉ๋ด๋ฅผ <span style='color:red'>๋ชจ๋</span> ์ ํํ์ธ์</h6>
<table>
<tr>
<td> 
<article class="AuthorityGroupGrid">
    <h5>๐ฑ๊ถํ๊ทธ๋ฃน</h5>
    <div align="center">
        <div id="myGrid1" class="ag-theme-balham" style="height:500px; width:500px; text-align: center;"></div>
    </div>
</article>
</td>
<td>
	<div class="menuButton">
     	<button id="saveButton" style="float:right; background-color:#F15F5F";> &nbsp;์ ์ฅ&nbsp; </button>
	</div>
<article class="MenuGrid">
	<h5>โ๏ธ๋ฉ๋ด</h5>
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

   let userAuthorityGroupList = []; // user์ ๊ถํ๊ทธ๋ฃน๋ฆฌ์คํธ๋ฅผ ๋ด๋ ๋ฐฐ์ด

   // ๊ถํ๊ทธ๋ฃน ๊ทธ๋ฆฌ๋
   let authorityGroupColumn = [
       {headerName: "๊ถํ๊ทธ๋ฃน๋ช", field: "authorityGroupName", checkboxSelection: true},
       {headerName: "๊ถํ๊ทธ๋ฃน์ฝ๋", field: "authorityGroupCode"}
     ];

   let authorityGroupRowData = [];
   let authorityGroupGridOptions = {
             columnDefs: authorityGroupColumn,
             rowSelection: 'single',
             rowData: authorityGroupRowData,
             defaultColDef: {editable: false},
             overlayNoRowsTemplate: "๊ถํ๊ทธ๋ฃน ์ ๋ณด๊ฐ ์์ต๋๋ค.",
             onGridReady: function (event) {
               event.api.sizeColumnsToFit();
             },
             onGridSizeChanged: function (event) {
               event.api.sizeColumnsToFit();
             },
             onRowClicked: function(event) {
                getMenuAuthorityData();
               }
           }

   // ๋ฉ๋ด ๊ทธ๋ฆฌ๋
   let menuAuthorityColumn = [
       {headerName: "๋ฉ๋ด", field: "menuName",
          headerCheckboxSelection: true,
          headerCheckboxSelectionFilteredOnly: true,
          checkboxSelection: function(row){
              if(row.data.authority == "1" || row.data.authority == "0")
               return true;
             }},
       {headerName: "๋ฉ๋ด์ฝ๋", field: "menuCode", hide:true},
       {headerName: "ํ์ฌ ๊ถํ์ฌ๋ถ", field: "authority", cellRenderer: function (params) {
            if (params.value == "1") {
               // params.node.setSelected(true);
                return params.value =  "๐ข" ;
            }else if(params.value == "-1" || params.value == "-2"){
               params.node.selectable = false;
               return params.value =  "" ;
            }
            return 'โ๏ธ' ;
           }
       }
     ];

   let menuAuthorityRowData = [];
   let menuAuthorityGridOptions = {
             columnDefs: menuAuthorityColumn,
             rowSelection: 'multiple',
             rowData: menuAuthorityRowData,
             defaultColDef: {editable: false},
             overlayNoRowsTemplate: "๋ฉ๋ด ์ ๋ณด๊ฐ ์์ต๋๋ค.",
             rowMultiSelectWithClick: true,
             onGridReady: function (event) {
               event.api.sizeColumnsToFit();
             },
             onGridSizeChanged: function (event) {
               event.api.sizeColumnsToFit();
             },
              getRowStyle: function (param) {
                  if(param.data.authority == "-1") {
                      return {'font-weight': 'bold'};
                  }
              },
               onSelectionChanged : function (event) {
                 selectedMenuList = this.api.getSelectedRows();
                 console.log(selectedMenuList);
              }
         }

   // ๊ถํ๊ทธ๋ฃน ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ ธ์ค๋ ํจ์
   let authorityGroupData;
   function getAuthorityGroupData(){
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/authoritymanager/getAuthorityGroup.do' +
            "?method=getAuthorityGroup",
            true);
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let txt = xhr.responseText;
                authorityGroupData = JSON.parse(txt).gridRowJson;
                console.log(authorityGroupData);
                if (txt.errorCode < 0) {
                    Swal.fire({
                        text: '๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฌ๋ค์ด๋๋ฐ ์ค๋ฅ๊ฐ ๋ฐ์ํ์ต๋๋ค.',
	  			        icon: 'error'
                    });
                    return;
                }
            }
        }
   }

   // ๊ถํ๊ทธ๋ฃน๋ณ ๋ฉ๋ด ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ ธ์ ๊ทธ๋ฆฌ๋์ ์ธํ. ๊ถํ๊ทธ๋ฃน ๊ทธ๋ฆฌ๋ ๋ก์ฐ๋ฅผ ํด๋ฆญํ์๋ ์ด๋ฒคํธ
   let menuAuthorityData;
   let authorityGroupCode;
   function getMenuAuthorityData(){
      authorityGoupRows = authorityGroupGridOptions.api.getSelectedRows();
      authorityGroupCode = authorityGoupRows[0].authorityGroupCode;
      console.log(authorityGroupCode);
        let xhr = new XMLHttpRequest();
        xhr.open('GET', '${pageContext.request.contextPath}/authoritymanager/getMenuAuthority.do' +
            "?method=getMenuAuthority&authorityGroupCode="+authorityGroupCode,
            true);
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let txt = xhr.responseText;
                menuAuthorityData = JSON.parse(txt).gridRowJson;
                console.log(menuAuthorityData);
                menuAuthorityGridOptions.api.setRowData(menuAuthorityData);
                if (txt.errorCode < 0) {
                    Swal.fire({
                        text: '๋ฐ์ดํฐ๋ฅผ ๋ถ๋ฌ๋ค์ด๋๋ฐ ์ค๋ฅ๊ฐ ๋ฐ์ํ์ต๋๋ค.',
                        icon: 'error'
                    });
                    return;
                }
            }
        }
   }

     // ์ ์ฅ๋ฒํผ ํด๋ฆญ์ด๋ฒคํธ
     saveButton.addEventListener("click", () => {

           Swal.fire({
               title: '๋ฉ๋ด๊ถํ ๋ณ๊ฒฝ',
               text: "๋ฉ๋ด๊ถํ์ ๋ณ๊ฒฝํ์๊ฒ ์ต๋๊น?",
               icon: 'warning',
               showCancelButton: true,
               confirmButtonColor: '#3085d6',
               cancelButtonColor: '#d33',
               cancelButtonText: '์ทจ์',
               confirmButtonText: 'ํ์ธ',
             }).then((result) => {
                if (result.isConfirmed) {
                 let selectmenuAuthorityData = [];
               let selectMenuData = menuAuthorityGridOptions.api.getSelectedRows();
               for(data of selectMenuData){
                  console.log(data.menuCode);
                  let newData = {authorityGroupCode:authorityGroupCode, menuCode: data.menuCode};
                  selectmenuAuthorityData.push(newData);
               }
               let insertData = JSON.stringify(selectmenuAuthorityData);
               console.log(insertData);

                    let xhr = new XMLHttpRequest();
                 xhr.open('GET', '${pageContext.request.contextPath}/authoritymanager/insertMenuAuthority.do' +
                     "?method=insertMenuAuthority"+
                     "&authorityGroupCode="+authorityGroupCode+
                     "&insertData="+encodeURI(insertData),
                     true);
                 xhr.setRequestHeader('Accept', 'application/json');
                 xhr.send();
                 xhr.onreadystatechange = () => {
                     if (xhr.readyState == 4 && xhr.status == 200) {
                        let txt = xhr.responseText;
                        getMenuAuthorityData();

                        if(userAuthorityGroupList.includes(authorityGroupCode)){
                           // ํ์ฌ user์ ๊ถํ๊ทธ๋ฃน๊ณผ ์ ํํ ๊ถํ๊ทธ๋ฃน์ด ์ผ์นํ๋ค๋ฉด
                           // ํ์ฌ ๋ก๊ทธ์ธ ํ user์ ์ ๋ณด๊ฐ ๋ณ๊ฒฝ๋์์ ๊ฒฝ์ฐ, session ์ฌ ์ธํ์ ์ํด ์ฌ๋ก๊ทธ์ธ์ด ํ์ํ๋ค.
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
                                   location.href="${pageContext.request.contextPath}/loginForm.html";
                                }
                             });
                        }else{
                           Swal.fire('๋ณ๊ฒฝ ์๋ฃ','๋ฉ๋ด๊ถํ์ด ์ ์์ ์ผ๋ก ๋ณ๊ฒฝ๋์์ต๋๋ค','success');
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
         new agGrid.Grid(myGrid1, authorityGroupGridOptions);
         authorityGroupGridOptions.api.setRowData(authorityGroupData);
         new agGrid.Grid(myGrid2, menuAuthorityGridOptions);
         /* menuAuthorityGridOptions.api.setRowData(menuAuthorityData); */
     }


     document.addEventListener('DOMContentLoaded', () => {

           userAuthorityGroupList = new Array(); // ํ์ฌ user์ ๊ถํ๊ทธ๋ฃน ๋ฆฌ์คํธ
            <c:forEach var="authorityGroup" items="${sessionScope.authorityGroupCode}">
               userAuthorityGroupList.push("${authorityGroup}");
         </c:forEach>

        getAuthorityGroupData();
        setTimeout("setGrid()",200);

        });
</script>
</body>
</html>