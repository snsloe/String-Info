import java.time.LocalDate;

public class Info {
    private String info;

    public Info(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String process() {
        String inputString = this.getInfo();
        String[] data;
        try {
            data = inputString.split(" ");
            if (data.length < 4) {
                throw new IllegalArgumentException("Некорректный формат ввода: недостаточно данных.");
            }
            String initials = getInitials(data[0], data[1], data[2]);
            String gender = getGender(data[2]);
            String age = getAge(data[3]);
            return "Инициалы: " + initials + "\n" + "Пол: " + gender + "\n" + "Возраст: " + age;
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Некорректный формат ввода: недостаточно данных.";
        } catch (NumberFormatException e) {
            return "Некорректный формат ввода: неверный формат даты.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String getInitials(String lastName, String firstName, String patronymic) {
        char[] nameInitial = firstName.toCharArray();
        char[] patrInitial = patronymic.toCharArray();

        return lastName + " " + nameInitial[0] + ". " + patrInitial[0] + ".";
    }

    public String getGender(String patronymic) {
        char[] patrChars = patronymic.toCharArray();
        String check = "";

        for (int i = patrChars.length - 3; i <= patrChars.length - 1; i++) {
            check += patrChars[i];
        }
        if (check.equals("вич")) {
            return "мужской";
        }
        else if (check.equals("вна")) {
            return "женский";
        }
        else {
            return "не удалось определить";
        }
    }

    public String getAge(String date) {
        String[] dateBirthString = date.split("\\.");
        int[] dateBirthdayInt = new int[3];
        int flagInt = 1;
        for (int i = 0; i < dateBirthString.length; i++) {
            if (!dateBirthString[i].matches("\\d+")) {
                flagInt = 0;
                break;
            } else {
                dateBirthdayInt[i] = Integer.parseInt(dateBirthString[i]);
            }
        }
        if (flagInt != 1) {
            return "Некорректный ввод даты рождения.";
        }
        try {
            LocalDate birthDate = LocalDate.of(dateBirthdayInt[2], dateBirthdayInt[1], dateBirthdayInt[0]);
            LocalDate currentDate = LocalDate.now();
            int[] dateCurrent = {currentDate.getDayOfMonth(), currentDate.getMonthValue(), currentDate.getYear()};
            int[] dateBirthday = {birthDate.getDayOfMonth(), birthDate.getMonthValue(), birthDate.getYear()};
            int flag = 0;
            if (dateCurrent[1] == dateBirthday[1]) {
                if (dateCurrent[0] >= dateBirthday[0]) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            } else if (dateCurrent[1] > dateBirthday[1]) {
                flag = 1;
            } else {
                flag = 0;
            }
            int age = (dateCurrent[2] - dateBirthday[2] - 1) + flag;
            if (age < 0) {
                return "Некорректный ввод даты рождения. Введенная дата должна быть раньше сегодняшней.";
            }
            return age + " " + postscript(Integer.toString(age));
        }
        catch (Exception e){
            return "Некорректный ввод даты рождения.";
        }
    }

    public String postscript(String age) {
        char[] year = age.toCharArray();
        char lastDigit = year[age.length() - 1];
        int curAge = Integer.parseInt(age);
        if (11 <= curAge && curAge <= 15)
            return "лет";
        else {

            if (lastDigit == '1') {
                return "год";
            } else if (lastDigit == '2' || lastDigit == '3' || lastDigit == '4') {
                return "года";
            } else {
                return "лет";
            }
        }
    }
}
