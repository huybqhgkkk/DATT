import { FormGroup } from '@angular/forms';
export function FileExtensionValidator(controlName: string, extensionRequire: string[] ) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
      const extension = control.value?.split('.').pop();
      if (!(extensionRequire.includes(extension))) {
        control.setErrors({ invalidImageFile: true });
      }
  };
}
