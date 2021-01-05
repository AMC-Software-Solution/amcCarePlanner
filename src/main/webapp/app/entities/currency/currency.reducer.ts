import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICurrency, defaultValue } from 'app/shared/model/currency.model';

export const ACTION_TYPES = {
  FETCH_CURRENCY_LIST: 'currency/FETCH_CURRENCY_LIST',
  FETCH_CURRENCY: 'currency/FETCH_CURRENCY',
  CREATE_CURRENCY: 'currency/CREATE_CURRENCY',
  UPDATE_CURRENCY: 'currency/UPDATE_CURRENCY',
  DELETE_CURRENCY: 'currency/DELETE_CURRENCY',
  SET_BLOB: 'currency/SET_BLOB',
  RESET: 'currency/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICurrency>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CurrencyState = Readonly<typeof initialState>;

// Reducer

export default (state: CurrencyState = initialState, action): CurrencyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CURRENCY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CURRENCY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CURRENCY):
    case REQUEST(ACTION_TYPES.UPDATE_CURRENCY):
    case REQUEST(ACTION_TYPES.DELETE_CURRENCY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CURRENCY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CURRENCY):
    case FAILURE(ACTION_TYPES.CREATE_CURRENCY):
    case FAILURE(ACTION_TYPES.UPDATE_CURRENCY):
    case FAILURE(ACTION_TYPES.DELETE_CURRENCY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENCY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENCY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CURRENCY):
    case SUCCESS(ACTION_TYPES.UPDATE_CURRENCY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CURRENCY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/v1';

// Actions

export const getEntities: ICrudGetAllAction<ICurrency> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-currencies-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CURRENCY_LIST,
    payload: axios.get<ICurrency>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICurrency> = id => {
  const requestUrl = `${apiUrl}/get-currency-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CURRENCY,
    payload: axios.get<ICurrency>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICurrency> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CURRENCY,
    payload: axios.post(apiUrl + '/create-currency-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICurrency> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CURRENCY,
    payload: axios.put(apiUrl + '/update-currency-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICurrency> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-currency-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CURRENCY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
