import 'package:amcCarePlanner/entities/question_type/question_type_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class QuestionTypeRepository {
    QuestionTypeRepository();
  
  static final String uriEndpoint = '/question-types';

  Future<List<QuestionType>> getAllQuestionTypes() async {
    final allQuestionTypesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<QuestionType>>(allQuestionTypesRequest.body);
  }

  Future<QuestionType> getQuestionType(int id) async {
    final questionTypeRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<QuestionType>(questionTypeRequest.body);
  }

  Future<QuestionType> create(QuestionType questionType) async {
    final questionTypeRequest = await HttpUtils.postRequest('$uriEndpoint', questionType);
    return JsonMapper.deserialize<QuestionType>(questionTypeRequest.body);
  }

  Future<QuestionType> update(QuestionType questionType) async {
    final questionTypeRequest = await HttpUtils.putRequest('$uriEndpoint', questionType);
    return JsonMapper.deserialize<QuestionType>(questionTypeRequest.body);
  }

  Future<void> delete(int id) async {
    final questionTypeRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
