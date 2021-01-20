import 'package:amcCarePlanner/entities/currency/currency_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class CurrencyRepository {
    CurrencyRepository();
  
  static final String uriEndpoint = '/currencies';

  Future<List<Currency>> getAllCurrencies() async {
    final allCurrenciesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Currency>>(allCurrenciesRequest.body);
  }

  Future<Currency> getCurrency(int id) async {
    final currencyRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Currency>(currencyRequest.body);
  }

  Future<Currency> create(Currency currency) async {
    final currencyRequest = await HttpUtils.postRequest('$uriEndpoint', currency);
    return JsonMapper.deserialize<Currency>(currencyRequest.body);
  }

  Future<Currency> update(Currency currency) async {
    final currencyRequest = await HttpUtils.putRequest('$uriEndpoint', currency);
    return JsonMapper.deserialize<Currency>(currencyRequest.body);
  }

  Future<void> delete(int id) async {
    final currencyRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
