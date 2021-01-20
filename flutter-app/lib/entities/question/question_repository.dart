import 'package:amcCarePlanner/entities/question/question_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class QuestionRepository {
    QuestionRepository();
  
  static final String uriEndpoint = '/questions';

  Future<List<Question>> getAllQuestions() async {
    final allQuestionsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Question>>(allQuestionsRequest.body);
  }

  Future<Question> getQuestion(int id) async {
    final questionRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Question>(questionRequest.body);
  }

  Future<Question> create(Question question) async {
    final questionRequest = await HttpUtils.postRequest('$uriEndpoint', question);
    return JsonMapper.deserialize<Question>(questionRequest.body);
  }

  Future<Question> update(Question question) async {
    final questionRequest = await HttpUtils.putRequest('$uriEndpoint', question);
    return JsonMapper.deserialize<Question>(questionRequest.body);
  }

  Future<void> delete(int id) async {
    final questionRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
