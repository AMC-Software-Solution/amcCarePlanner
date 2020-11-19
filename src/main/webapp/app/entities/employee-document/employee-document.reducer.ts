import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeDocument, defaultValue } from 'app/shared/model/employee-document.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEDOCUMENT_LIST: 'employeeDocument/FETCH_EMPLOYEEDOCUMENT_LIST',
  FETCH_EMPLOYEEDOCUMENT: 'employeeDocument/FETCH_EMPLOYEEDOCUMENT',
  CREATE_EMPLOYEEDOCUMENT: 'employeeDocument/CREATE_EMPLOYEEDOCUMENT',
  UPDATE_EMPLOYEEDOCUMENT: 'employeeDocument/UPDATE_EMPLOYEEDOCUMENT',
  DELETE_EMPLOYEEDOCUMENT: 'employeeDocument/DELETE_EMPLOYEEDOCUMENT',
  SET_BLOB: 'employeeDocument/SET_BLOB',
  RESET: 'employeeDocument/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeDocument>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmployeeDocumentState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeDocumentState = initialState, action): EmployeeDocumentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEDOCUMENT):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEDOCUMENT):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEDOCUMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEDOCUMENT):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEDOCUMENT):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEDOCUMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEDOCUMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEDOCUMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEDOCUMENT):
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

const apiUrl = 'api/employee-documents';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeDocument> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT_LIST,
    payload: axios.get<IEmployeeDocument>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmployeeDocument> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEDOCUMENT,
    payload: axios.get<IEmployeeDocument>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEDOCUMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeDocument> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEDOCUMENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeDocument> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEDOCUMENT,
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
