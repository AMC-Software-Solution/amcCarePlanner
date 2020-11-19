import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEquality, defaultValue } from 'app/shared/model/equality.model';

export const ACTION_TYPES = {
  FETCH_EQUALITY_LIST: 'equality/FETCH_EQUALITY_LIST',
  FETCH_EQUALITY: 'equality/FETCH_EQUALITY',
  CREATE_EQUALITY: 'equality/CREATE_EQUALITY',
  UPDATE_EQUALITY: 'equality/UPDATE_EQUALITY',
  DELETE_EQUALITY: 'equality/DELETE_EQUALITY',
  RESET: 'equality/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEquality>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EqualityState = Readonly<typeof initialState>;

// Reducer

export default (state: EqualityState = initialState, action): EqualityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EQUALITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EQUALITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EQUALITY):
    case REQUEST(ACTION_TYPES.UPDATE_EQUALITY):
    case REQUEST(ACTION_TYPES.DELETE_EQUALITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EQUALITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EQUALITY):
    case FAILURE(ACTION_TYPES.CREATE_EQUALITY):
    case FAILURE(ACTION_TYPES.UPDATE_EQUALITY):
    case FAILURE(ACTION_TYPES.DELETE_EQUALITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EQUALITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EQUALITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EQUALITY):
    case SUCCESS(ACTION_TYPES.UPDATE_EQUALITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EQUALITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/equalities';

// Actions

export const getEntities: ICrudGetAllAction<IEquality> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EQUALITY_LIST,
    payload: axios.get<IEquality>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEquality> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EQUALITY,
    payload: axios.get<IEquality>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEquality> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EQUALITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEquality> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EQUALITY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEquality> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EQUALITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
