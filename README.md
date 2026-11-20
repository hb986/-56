# عنتيل المحله

تطبيق Android كوميدي بواجهة خضراء، يحتوي على 9 أزرار بأسماء ملفات الصوت المرسلة وزر عاشر عشوائي.

## البناء
يتم بناء APK تلقائياً عبر GitHub Actions عند كل push أو يدوياً من تبويب Actions.

## ملفات الصوت
ضع ملفات MP3 التسعة داخل `app/src/main/res/raw/` بأسماء موارد Android التالية:
`zabo_kebir.mp3`, `batandora_elhamra.mp3`, `fokhany.mp3`, `wahed.mp3`, `sayek.mp3`, `betengany_adim.mp3`, `maleh.mp3`, `zabri_manga.mp3`, `almani.mp3`.

**ملاحظة:** هذه النسخة الأولية تستخدم نغمات نظام مؤقتة للأزرار إلى أن تُضاف ملفات MP3 الأصلية؛ زر العشوائي يعمل باختيار زر من التسعة.
