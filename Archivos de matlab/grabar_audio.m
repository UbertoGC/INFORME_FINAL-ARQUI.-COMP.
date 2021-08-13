%t %variable de tiempo en segundos%
%Fs %variable de la frecuencia%
t = 2;
Fs = 44100;
v = audiorecorder(Fs, 24, 1);
v.StartFcn = 'disp(''   iniciando grabación'')';
v.StopFcn = 'disp(''   terminando grabación'')';

recordblocking(v, t);
x = v.getaudiodata();
audiowrite ('grabacion1.wav',x,Fs);
which 'grabacion2.wav'

close all;
clear all;

%%
g = audioread('grabacion1.wav');

g = normalizar(g);
voz2 = abs(fft (g)); % se obtiene la transformada de fourier de la grabacion temporal%
voz2 = voz2.*conj (voz2); % se obtiene el conjugado% 


p = audioread('Grabacion.wav');

p = normalizar(p);
vozp = abs(fft (p)); % se obtiene la transformada de fourier de la grabacion contraseña%
vozp = vozp.*conj (vozp); % se obtiene el conjugado% 

%%
error(1)=2.4000e+03;
disp('Correlacion de Error  contraseña:')
error(2) = mean(abs(voz2-vozp));
disp(error(2))

min_error = min(error);
if error(1)>min_error
    disp('Contraseña correcta')
else
    
    disp('Error, contraseña incorrecta')
end