import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDisability, defaultValue } from 'app/shared/model/disability.model';

export const ACTION_TYPES = {
  FETCH_DISABILITY_LIST: 'disability/FETCH_DISABILITY_LIST',
  FETCH_DISABILITY: 'disability/FETCH_DISABILITY',
  CREATE_DISABILITY: 'disability/CREATE_DISABILITY',
  UPDATE_DISABILITY: 'disability/UPDATE_DISABILITY',
  DELETE_DISABILITY: 'disability/DELETE_DISABILITY',
  RESET: 'disability/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDisability>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type DisabilityState = Readonly<typeof initialState>;

// Reducer

export default (state: DisabilityState = initialState, action): DisabilityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISABILITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISABILITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DISABILITY):
    case REQUEST(ACTION_TYPES.UPDATE_DISABILITY):
    case REQUEST(ACTION_TYPES.DELETE_DISABILITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DISABILITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISABILITY):
    case FAILURE(ACTION_TYPES.CREATE_DISABILITY):
    case FAILURE(ACTION_TYPES.UPDATE_DISABILITY):
    case FAILURE(ACTION_TYPES.DELETE_DISABILITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISABILITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISABILITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISABILITY):
    case SUCCESS(ACTION_TYPES.UPDATE_DISABILITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISABILITY):
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

const apiUrl = 'api/v1';

// Actions

export const getEntities: ICrudGetAllAction<IDisability> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-disabilities-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISABILITY_LIST,
    payload: axios.get<IDisability>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IDisability> = id => {
  const requestUrl = `${apiUrl}/get-disability-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISABILITY,
    payload: axios.get<IDisability>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDisability> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISABILITY,
    payload: axios.post(apiUrl + '/create-disability-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDisability> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISABILITY,
    payload: axios.put(apiUrl + '/update-disability-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDisability> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-disability-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISABILITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
