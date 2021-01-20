import 'package:amcCarePlanner/entities/answer/answer_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class AnswerRepository {
    AnswerRepository();
  
  static final String uriEndpoint = '/answers';

  Future<List<Answer>> getAllAnswers() async {
    final allAnswersRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Answer>>(allAnswersRequest.body);
  }

  Future<Answer> getAnswer(int id) async {
    final answerRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Answer>(answerRequest.body);
  }

  Future<Answer> create(Answer answer) async {
    final answerRequest = await HttpUtils.postRequest('$uriEndpoint', answer);
    return JsonMapper.deserialize<Answer>(answerRequest.body);
  }

  Future<Answer> update(Answer answer) async {
    final answerRequest = await HttpUtils.putRequest('$uriEndpoint', answer);
    return JsonMapper.deserialize<Answer>(answerRequest.body);
  }

  Future<void> delete(int id) async {
    final answerRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
