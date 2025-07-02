import Header from "./header";
import Navbar from "./navbar";

export default function Layout({children}){
    return(
        //component 합성으로 Header와 Navbar 갖고 오기 10:45
        <>
            <Header/>
            <Navbar/>
            {children}
        </>
    )
}