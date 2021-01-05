import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDisabilityType, defaultValue } from 'app/shared/model/disability-type.model';

export const ACTION_TYPES = {
  FETCH_DISABILITYTYPE_LIST: 'disabilityType/FETCH_DISABILITYTYPE_LIST',
  FETCH_DISABILITYTYPE: 'disabilityType/FETCH_DISABILITYTYPE',
  CREATE_DISABILITYTYPE: 'disabilityType/CREATE_DISABILITYTYPE',
  UPDATE_DISABILITYTYPE: 'disabilityType/UPDATE_DISABILITYTYPE',
  DELETE_DISABILITYTYPE: 'disabilityType/DELETE_DISABILITYTYPE',
  RESET: 'disabilityType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDisabilityType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type DisabilityTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: DisabilityTypeState = initialState, action): DisabilityTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISABILITYTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISABILITYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DISABILITYTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_DISABILITYTYPE):
    case REQUEST(ACTION_TYPES.DELETE_DISABILITYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DISABILITYTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISABILITYTYPE):
    case FAILURE(ACTION_TYPES.CREATE_DISABILITYTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_DISABILITYTYPE):
    case FAILURE(ACTION_TYPES.DELETE_DISABILITYTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISABILITYTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISABILITYTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISABILITYTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_DISABILITYTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISABILITYTYPE):
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

export const getEntities: ICrudGetAllAction<IDisabilityType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-disability-types-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISABILITYTYPE_LIST,
    payload: axios.get<IDisabilityType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IDisabilityType> = id => {
  const requestUrl = `${apiUrl}/get-disability-type-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISABILITYTYPE,
    payload: axios.get<IDisabilityType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDisabilityType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISABILITYTYPE,
    payload: axios.post(apiUrl + '/create-disability-type-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDisabilityType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISABILITYTYPE,
    payload: axios.put(apiUrl + '/update-disability-type-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDisabilityType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-disability-type-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISABILITYTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
