import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEligibilityType, defaultValue } from 'app/shared/model/eligibility-type.model';

export const ACTION_TYPES = {
  FETCH_ELIGIBILITYTYPE_LIST: 'eligibilityType/FETCH_ELIGIBILITYTYPE_LIST',
  FETCH_ELIGIBILITYTYPE: 'eligibilityType/FETCH_ELIGIBILITYTYPE',
  CREATE_ELIGIBILITYTYPE: 'eligibilityType/CREATE_ELIGIBILITYTYPE',
  UPDATE_ELIGIBILITYTYPE: 'eligibilityType/UPDATE_ELIGIBILITYTYPE',
  DELETE_ELIGIBILITYTYPE: 'eligibilityType/DELETE_ELIGIBILITYTYPE',
  RESET: 'eligibilityType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEligibilityType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EligibilityTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: EligibilityTypeState = initialState, action): EligibilityTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ELIGIBILITYTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ELIGIBILITYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ELIGIBILITYTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_ELIGIBILITYTYPE):
    case REQUEST(ACTION_TYPES.DELETE_ELIGIBILITYTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ELIGIBILITYTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ELIGIBILITYTYPE):
    case FAILURE(ACTION_TYPES.CREATE_ELIGIBILITYTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_ELIGIBILITYTYPE):
    case FAILURE(ACTION_TYPES.DELETE_ELIGIBILITYTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ELIGIBILITYTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ELIGIBILITYTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ELIGIBILITYTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_ELIGIBILITYTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ELIGIBILITYTYPE):
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

export const getEntities: ICrudGetAllAction<IEligibilityType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ELIGIBILITYTYPE_LIST,
    payload: axios.get<IEligibilityType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEligibilityType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ELIGIBILITYTYPE,
    payload: axios.get<IEligibilityType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEligibilityType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ELIGIBILITYTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEligibilityType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ELIGIBILITYTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEligibilityType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ELIGIBILITYTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
