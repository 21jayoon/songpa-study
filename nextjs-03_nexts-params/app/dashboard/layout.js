export default function DashboardLayout({children}){
    return(
        <>        
            <h3>Dashboard menu</h3>         
            <ul>
                <li>Statistics</li>
                <li>Users</li>
                <li>Settings</li>
            </ul>
            {children} {/**여기에 dashboard/page.js가 랜더링됨 11:07 */}
        </>
    )
}