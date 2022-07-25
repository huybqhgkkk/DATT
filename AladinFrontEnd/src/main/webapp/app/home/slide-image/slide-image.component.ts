import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-slide-image',
  templateUrl: './slide-image.component.html',
  styleUrls: ['./slide-image.component.scss'],
})
export class SlideImageComponent {
  ok = false;
  slideIndex = 1;

  // plusSlides() {
  //   showSlides();
  // }

  // showSlides():void {
  //   let i;
  //   let slides = document.getElementsByClassName("mySlides");
  //   let dots = document.getElementsByClassName("dot");
  //   if (this.slideIndex > slides.length) {this.slideIndex = 1}
  //   if (this.slideIndex < 1) {this.slideIndex = slides.length}
  //   for (i = 0; i < slides.length; i++) {
  //     slides[i].setAttribute();
  //   }
  //   for (i = 0; i < dots.length; i++) {
  //     dots[i].className = dots[i].className.replace(" active", "");
  //   }
  //   slides[slideIndex-1].style.display = "block";
  //   dots[slideIndex-1].className += " active";
  // }
}
