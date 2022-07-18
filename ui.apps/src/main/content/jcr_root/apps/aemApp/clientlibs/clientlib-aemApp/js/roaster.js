let fromSlide = 0
let toSlide = 3
var curr=1;
var totalSlide=document.getElementsByClassName("roasterContent");

shoWSlides(fromSlide,toSlide,curr)

function nextSlide() {
curr+=1
// alert("curr: "+curr)
   fromSlide+=4;
   toSlide+=4;
 
 
   if (toSlide>=totalSlide.length) {
   document.getElementById("btn-right").disabled=true;
   document.getElementById("btn-right").className = "btn-disabled"; 
  
 
   }
   document.getElementById("btn-left").disabled=false;
   document.getElementById("btn-left").className = "btn-left"; 
   shoWSlides(fromSlide,toSlide,curr)


}
function previousSlide() {
    curr-=1;
    fromSlide-=4;
   toSlide-=4;
   if (fromSlide==0) {
    document.getElementById("btn-left").disabled=true;
    document.getElementById("btn-left").className = "btn-disabled"; 
   }
   document.getElementById("btn-right").disabled=false;
   document.getElementById("btn-right").className = "btn-right"; 
   shoWSlides(fromSlide,toSlide,curr)
}


function shoWSlides(fromSlide, toSlide,current) {
    
   
    setTimeout(() => {
        if (current==1) {
            document.getElementById("btn-left").className = "btn-disabled"; 
            document.getElementById("btn-left").disabled=true;
           }
    
           if (toSlide>=totalSlide.length) {
            document.getElementById("btn-right").disabled=true;
            document.getElementById("btn-right").className = "btn-disabled"; }
        var slides = document.getElementsByClassName("roasterContent");
        // alert(current)
        for (let i = 0; i < totalSlide.length; i++) {
            slides[i].style.display = "none";
            
        }
        document.getElementById("curr").innerHTML=current
        document.getElementById("tot").innerHTML=Math.ceil(slides.length/4)
       for (let i = fromSlide; i <= toSlide; i++) {
        slides[i].style.display = "block";
        
       }
      
      
       
    }, 100);
}
