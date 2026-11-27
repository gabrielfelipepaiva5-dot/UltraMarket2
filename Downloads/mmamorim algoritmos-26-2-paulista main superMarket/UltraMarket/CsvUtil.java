package UltraMarket;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static void salvar(Object[] objetos, String nomeArquivo) {
        new File(nomeArquivo).getParentFile().mkdirs();

        if (objetos == null || objetos.length == 0) {
            try (FileWriter vazio = new FileWriter(nomeArquivo)) {
                // lista vazia: deixa o arquivo vazio
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            Class<?> clazz = objetos[0].getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                writer.append(fields[i].getName());
                if (i < fields.length - 1)
                    writer.append(";");
            }
            writer.append("\n");

            for (Object obj : objetos) {
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    Object valor = fields[i].get(obj);
                    writer.append(String.valueOf(valor));
                    if (i < fields.length - 1)
                        writer.append(";");
                }
                writer.append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] ler(String nomeArquivo, Class<T> entity) {
        List<T> lista = new ArrayList<>();

        if (!new File(nomeArquivo).exists()) {
            return (T[]) Array.newInstance(entity, 0);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {

            String header = reader.readLine();
            if (header == null)
                return (T[]) Array.newInstance(entity, 0);

            String[] nomesCampos = header.split(";");

            String linha;
            while ((linha = reader.readLine()) != null) {

                String[] valores = linha.split(";", -1);
                T obj = entity.getDeclaredConstructor().newInstance();

                for (int i = 0; i < nomesCampos.length; i++) {
                    Field field = entity.getDeclaredField(nomesCampos[i]);
                    field.setAccessible(true);

                    Class<?> tipo = field.getType();
                    String valor = valores[i];

                    if (tipo == int.class || tipo == Integer.class) {
                        field.set(obj, Integer.parseInt(valor));
                    } else if (tipo == double.class || tipo == Double.class) {
                        field.set(obj, Double.parseDouble(valor));
                    } else if (tipo == boolean.class || tipo == Boolean.class) {
                        field.set(obj, Boolean.parseBoolean(valor));
                    } else {
                        field.set(obj, valor);
                    }
                }

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        T[] array = (T[]) Array.newInstance(entity, lista.size());
        return lista.toArray(array);
    }
}