/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.openkp.business.walidacja;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pl.openkp.business.pracownicy.entity.Pracownik;

public class ValidPassengerCountValidator implements ConstraintValidator<ValidPassengerCount, Pracownik> {

    @Override
    public void initialize(ValidPassengerCount constraintAnnotation) {
    }

    @Override
    public boolean isValid(Pracownik car, ConstraintValidatorContext context) {
        if (car == null) {
            return true;
        }

        return car.getWynagrodzenie().intValue() > 10000;
    }
}