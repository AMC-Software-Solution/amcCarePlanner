import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeAvailability, defaultValue } from 'app/shared/model/employee-availability.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEAVAILABILITY_LIST: 'employeeAvailability/FETCH_EMPLOYEEAVAILABILITY_LIST',
  FETCH_EMPLOYEEAVAILABILITY: 'employeeAvailability/FETCH_EMPLOYEEAVAILABILITY',
  CREATE_EMPLOYEEAVAILABILITY: 'employeeAvailability/CREATE_EMPLOYEEAVAILABILITY',
  UPDATE_EMPLOYEEAVAILABILITY: 'employeeAvailability/UPDATE_EMPLOYEEAVAILABILITY',
  DELETE_EMPLOYEEAVAILABILITY: 'employeeAvailability/DELETE_EMPLOYEEAVAILABILITY',
  RESET: 'employeeAvailability/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeAvailability>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmployeeAvailabilityState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeAvailabilityState = initialState, action): EmployeeAvailabilityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEAVAILABILITY):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEAVAILABILITY):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEAVAILABILITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEAVAILABILITY):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEAVAILABILITY):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEAVAILABILITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEAVAILABILITY):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEAVAILABILITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEAVAILABILITY):
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

const apiUrl = 'api/employee-availabilities';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeAvailability> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY_LIST,
    payload: axios.get<IEmployeeAvailability>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmployeeAvailability> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEAVAILABILITY,
    payload: axios.get<IEmployeeAvailability>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeAvailability> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEAVAILABILITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeAvailability> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEAVAILABILITY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeAvailability> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEAVAILABILITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
