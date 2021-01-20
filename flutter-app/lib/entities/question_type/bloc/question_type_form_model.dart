import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/question_type/question_type_model.dart';

enum QuestionTypeValidationError { invalid }
class QuestionTypeInput extends FormzInput<String, QuestionTypeValidationError> {
  const QuestionTypeInput.pure() : super.pure('');
  const QuestionTypeInput.dirty([String value = '']) : super.dirty(value);

  @override
  QuestionTypeValidationError validator(String value) {
    return null;
  }
}

enum LastUpdatedDateValidationError { invalid }
class LastUpdatedDateInput extends FormzInput<DateTime, LastUpdatedDateValidationError> {
  LastUpdatedDateInput.pure() : super.pure(DateTime.now());
  LastUpdatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  LastUpdatedDateValidationError validator(DateTime value) {
    return null;
  }
}

