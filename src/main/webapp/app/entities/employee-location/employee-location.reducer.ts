import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeLocation, defaultValue } from 'app/shared/model/employee-location.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEELOCATION_LIST: 'employeeLocation/FETCH_EMPLOYEELOCATION_LIST',
  FETCH_EMPLOYEELOCATION: 'employeeLocation/FETCH_EMPLOYEELOCATION',
  CREATE_EMPLOYEELOCATION: 'employeeLocation/CREATE_EMPLOYEELOCATION',
  UPDATE_EMPLOYEELOCATION: 'employeeLocation/UPDATE_EMPLOYEELOCATION',
  DELETE_EMPLOYEELOCATION: 'employeeLocation/DELETE_EMPLOYEELOCATION',
  RESET: 'employeeLocation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeLocation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmployeeLocationState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeLocationState = initialState, action): EmployeeLocationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEELOCATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEELOCATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEELOCATION):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEELOCATION):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEELOCATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEELOCATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEELOCATION):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEELOCATION):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEELOCATION):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEELOCATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEELOCATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEELOCATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEELOCATION):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEELOCATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEELOCATION):
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

export const getEntities: ICrudGetAllAction<IEmployeeLocation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-employee-locations-by-client-id/${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEELOCATION_LIST,
    payload: axios.get<IEmployeeLocation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmployeeLocation> = id => {
  const requestUrl = `${apiUrl}//get-all-employee-locations-by-client-id${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEELOCATION,
    payload: axios.get<IEmployeeLocation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeLocation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEELOCATION,
    payload: axios.post(apiUrl + '/create-employee-location-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeLocation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEELOCATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeLocation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEELOCATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
