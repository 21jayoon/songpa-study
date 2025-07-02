import Link from "next/link"
//Link를 썼기 때문에 import Link 한다 13:12
import itemStyle from "./MenuItem.module.css"
//작성한 MenuItem.module.css 파일 import  13:09

export default function MenuItem({menu}){
    /*menu 폴더 하위 page.js에서 
     <MenuItem key={menu.menuCode} menu={menu}를 통해
     내려준 {menu}라는 props를 여기서도 받아와야
     아래 {menu.menuName}의 menu를 찾아올 수 있다.  12:14 */
    return(
        <Link href={`/menu/${menu.menuCode}`}>
            <div className={itemStyle.MenuItem}>
                    <h3>Menu name : {menu.menuName}</h3>
                    <h3>price: {menu.menuPrice}</h3>
                    <h3>Type of menu: {menu.categoryName}</h3>
            </div>
            {/* 1차 테이블화(1차 수정)
            <table border="1">
                <tr> Menu name:
                    <td>{menu.menuName} </td>
                        Price: 
                    <td>{menu.menuPrice}</td>
                        Type of menu:
                    <td>{menu.categoryName}</td>
                </tr>
            </table>*/}
            {/* 2차 테이블화(2차 수정)
            <table border="1" >
                {/**https://developer.mozilla.org/ko/docs/Web/HTML/Reference/Elements/thead
                <thead>
                    <tr>
                        <th scope="col">Menu name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Type of menu</th>
                    </tr>
                </thead>
                <tbody>
                    {/**In HTML, text nodes cannot be a child of <tr>.
                     * This will cause a hydration error.  
                     * -> 아래 {menu.menuName}...시리즈 모두 text nodes 12:25*/}
                     {/**In HTML, <td> cannot be a child of <tbody>. 
                      * <- {} 양 옆에 <tr>태그를 <td>태그로 바꿨더니
                      * 새로 생긴 오류 12:30 */}
                    {/*<tr><td>{menu.menuName}</td></tr>  아래 코드로 수정  12:35
                    <tr><th scope="row">{menu.menuName}</th></tr>
                    {/*<tr><td>{menu.menuPrice}</td></tr>  수정 12:35(2)
                    <tr><th scope="row">{menu.menuPrice}</th></tr>
                    <tr><td>{menu.categoryName}</td></tr>
                    </tbody>  */}
                    {/** tbody코드 전체 수정  12:41 
                     * <tr>
                        <td>{menu.menuName}</td>
                        <td>{menu.menuPrice}</td>
                        <td>{menu.categoryName}</td>
                    </tr> <- 수정 전
                     * Menu name	Price	Type of menu
                     * 열무김치라떼	4500	쥬스
                     * 우럭스무디	5000	쥬스   
                     * 표를 이런 식으로 진짜 메뉴판 느낌처럼 만들고 싶은데
                     * Menu name	Price	Type of menu
                     * 열무김치라떼	4500	쥬스
                     * Menu name	Price	Type of menu
                     * 우럭스무디	5000	쥬스
                     * 이렇게만 나왔다. 그래서 copilot에 물어보니 아래와 같이 만들라고 해서, 고친다  13:04
                     * <tbody>
                     *     {menuList.map(menu => (
                     *      <tr key={menu.menuCode}>
                     *         <td>{menu.menuName}</td>
                     *         <td>{menu.menuPrice}</td>
                     *         <td>{menu.categoryName}</td>
                     *       </tr>
                     *     ))}
                     *   </tbody>
                </tbody>
            </table>*/}
        </Link>
    )
}