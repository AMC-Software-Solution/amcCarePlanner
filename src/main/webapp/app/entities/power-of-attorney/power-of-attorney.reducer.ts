import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPowerOfAttorney, defaultValue } from 'app/shared/model/power-of-attorney.model';

export const ACTION_TYPES = {
  FETCH_POWEROFATTORNEY_LIST: 'powerOfAttorney/FETCH_POWEROFATTORNEY_LIST',
  FETCH_POWEROFATTORNEY: 'powerOfAttorney/FETCH_POWEROFATTORNEY',
  CREATE_POWEROFATTORNEY: 'powerOfAttorney/CREATE_POWEROFATTORNEY',
  UPDATE_POWEROFATTORNEY: 'powerOfAttorney/UPDATE_POWEROFATTORNEY',
  DELETE_POWEROFATTORNEY: 'powerOfAttorney/DELETE_POWEROFATTORNEY',
  RESET: 'powerOfAttorney/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPowerOfAttorney>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PowerOfAttorneyState = Readonly<typeof initialState>;

// Reducer

export default (state: PowerOfAttorneyState = initialState, action): PowerOfAttorneyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POWEROFATTORNEY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POWEROFATTORNEY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_POWEROFATTORNEY):
    case REQUEST(ACTION_TYPES.UPDATE_POWEROFATTORNEY):
    case REQUEST(ACTION_TYPES.DELETE_POWEROFATTORNEY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_POWEROFATTORNEY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POWEROFATTORNEY):
    case FAILURE(ACTION_TYPES.CREATE_POWEROFATTORNEY):
    case FAILURE(ACTION_TYPES.UPDATE_POWEROFATTORNEY):
    case FAILURE(ACTION_TYPES.DELETE_POWEROFATTORNEY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POWEROFATTORNEY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_POWEROFATTORNEY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_POWEROFATTORNEY):
    case SUCCESS(ACTION_TYPES.UPDATE_POWEROFATTORNEY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_POWEROFATTORNEY):
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

const apiUrl = 'api/power-of-attorneys';

// Actions

export const getEntities: ICrudGetAllAction<IPowerOfAttorney> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_POWEROFATTORNEY_LIST,
    payload: axios.get<IPowerOfAttorney>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPowerOfAttorney> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POWEROFATTORNEY,
    payload: axios.get<IPowerOfAttorney>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPowerOfAttorney> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POWEROFATTORNEY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPowerOfAttorney> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POWEROFATTORNEY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPowerOfAttorney> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POWEROFATTORNEY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
