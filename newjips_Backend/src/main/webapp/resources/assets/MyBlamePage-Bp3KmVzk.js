import{b as U,a as k,c as B,d as S,e as I,f as A,_ as h,g as u,h as v,i as C,j as L,k as z,l as F}from"./face7-CZFoiLU3.js";import{_ as N}from"./SideBar-C5GIFswZ.js";import{f as R,a as V}from"./face3-DZ59yOSB.js";import{c as d,d as _,o as s,f as e,g as t,i as j,F as m,q as g,t as c}from"./index-HatRJsUW.js";const q={class:"container-fluid"},D={class:"row px-xl-3"},E={class:"col-lg-2"},G={class:"col-lg-9 col-md-9 col-sm-12"},M=t("h2",{class:"mt-5",style:{"margin-left":"20px","margin-right":"0","margin-bottom":"20px"}},"신고한 매물",-1),P={class:"position-relative"},$={key:0},H=t("img",{src:h,alt:"Left Arrow"},null,-1),J=[H],K={class:"row"},O={class:"card hover-animate h-100 border-0 shadow card-custom"},Q={class:"card-img-top overflow-hidden img-container"},T=["src","alt"],W={class:"card-body"},X={class:"card-title"},Y={class:"card-text"},Z=t("img",{src:u,alt:"Right Arrow"},null,-1),tt=[Z],st={key:1},et=t("div",{class:"text-center"},[t("img",{src:v,alt:"nothing",class:"img-fluid",style:{"max-width":"300px"}}),t("p",null,"신고한 매물이 없습니다.")],-1),it=[et],lt=t("h2",{class:"mt-4",style:{"margin-left":"20px","margin-right":"0","margin-bottom":"20px"}},"신고한 버디즈",-1),ot={class:"position-relative"},at={key:0},ct=t("img",{src:h,alt:"Left Arrow"},null,-1),rt=[ct],nt={class:"row"},dt={class:"card hover-animate h-100 border-0 shadow card-custom"},_t={class:"card-img-top overflow-hidden img-container"},mt=["src","alt"],gt={class:"card-body"},ht={class:"card-title"},ut={class:"card-text"},vt=t("img",{src:u,alt:"Right Arrow"},null,-1),pt=[vt],ft={key:1},bt=t("div",{class:"text-center"},[t("img",{src:v,alt:"nothing",class:"img-fluid",style:{"max-width":"300px"}}),t("p",null,"신고한 매물이 없습니다.")],-1),xt=[bt],At={__name:"MyBlamePage",setup(wt){const i=d(0),l=d(0),a=[{title:"매물 1",description:"도심에 위치한 넓고 현대적인 방입니다.",imageUrl:U},{title:"매물 2",description:"교통이 편리한 위치에 있는 아파트입니다.",imageUrl:k},{title:"매물 3",description:"바다가 보이는 멋진 스튜디오입니다.",imageUrl:B},{title:"매물 4",description:"조용하고 평화로운 시골 주택입니다.",imageUrl:S},{title:"매물 5",description:"럭셔리 스타일의 고급 주택입니다.",imageUrl:I},{title:"매물 6",description:"현대적인 디자인의 도심형 아파트입니다.",imageUrl:A}],r=[{title:"버디 1",description:"활기찬 성격을 가진 친구입니다.",imageUrl:R},{title:"버디 2",description:"재미있는 이야기로 항상 즐거운 친구입니다.",imageUrl:V},{title:"버디 3",description:"모험을 좋아하는 친구입니다.",imageUrl:C},{title:"버디 4",description:"조용하고 사려 깊은 친구입니다.",imageUrl:L},{title:"버디 5",description:"유머감각이 뛰어난 친구입니다.",imageUrl:z},{title:"버디 6",description:"운동을 사랑하는 친구입니다.",imageUrl:F}],p=_(()=>a.slice(i.value,i.value+4)),f=()=>{i.value+4>=a.length?i.value=0:i.value+=1},b=()=>{i.value===0?i.value=a.length-4:i.value-=1},x=_(()=>r.slice(l.value,l.value+4)),w=()=>{l.value+4>=r.length?l.value=0:l.value+=1},y=()=>{l.value===0?l.value=r.length-4:l.value-=1};return(yt,Ut)=>(s(),e("div",q,[t("div",D,[t("div",E,[j(N)]),t("div",G,[M,t("div",P,[a.length>0?(s(),e("div",$,[t("button",{onClick:b,class:"arrow-left"},J),t("div",K,[(s(!0),e(m,null,g(p.value,(o,n)=>(s(),e("div",{class:"col-lg-3 col-md-4 mb-4",key:n},[t("div",O,[t("div",Q,[t("img",{src:o.imageUrl,class:"img-fluid img-custom",alt:a.title},null,8,T)]),t("div",W,[t("h5",X,c(o.title),1),t("p",Y,c(o.description),1)])])]))),128))]),t("button",{onClick:f,class:"arrow-right"},tt)])):(s(),e("div",st,it))]),lt,t("div",ot,[a.length>0?(s(),e("div",at,[t("button",{onClick:y,class:"arrow-left"},rt),t("div",nt,[(s(!0),e(m,null,g(x.value,(o,n)=>(s(),e("div",{class:"col-lg-3 col-md-4 mb-4",key:n},[t("div",dt,[t("div",_t,[t("img",{src:o.imageUrl,class:"img-fluid img-custom",alt:o.title},null,8,mt)]),t("div",gt,[t("h5",ht,c(o.title),1),t("p",ut,c(o.description),1)])])]))),128))]),t("button",{onClick:w,class:"arrow-right"},pt)])):(s(),e("div",ft,xt))])])])]))}};export{At as default};
